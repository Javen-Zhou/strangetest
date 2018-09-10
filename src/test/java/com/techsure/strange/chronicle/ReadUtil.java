package com.techsure.strange.chronicle;

import com.techsure.strange.hazelcast.assist.DataTypeEnum;
import com.techsure.strange.hazelcast.assist.RollupVo;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.wire.WireIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/8/31 星期五 10:15
 */
public class ReadUtil {
		private static final Logger logger = LoggerFactory.getLogger(ReadUtil.class);

		/**
		 * 读取一个rollUpVo
		 *
		 * @return rollUpVo
		 */
		@SuppressWarnings("unchecked")
		public static RollupVo read(ExcerptTailer tailer) {
			RollupVo rollupVo = new RollupVo();
			Boolean result = tailer.readDocument(w -> w.read(TopicEnum.ROLLUP.getTopic()).marshallable(
					m -> {
						try {
							rollupVo.setTenant(m.read(TopicEnum.TENANT.getTopic()).text());
							rollupVo.setTable(m.read(TopicEnum.TABLE.getTopic()).text());
							rollupVo.setMetricId(m.read(TopicEnum.METRIC_ID.getTopic()).text());
							rollupVo.setDataType(m.read(TopicEnum.DATA_TYPE.getTopic()).text());
							rollupVo.setTtl(m.read(TopicEnum.TTL.getTopic()).int32());
							rollupVo.setTime(m.read(TopicEnum.TIME.getTopic()).int64());
							rollupVo.setNum(m.read(TopicEnum.NUM.getTopic()).int32());

							String dataType = rollupVo.getDataType();
							if (!DataTypeEnum.STRING.getType().equals(dataType)) {
								rollupVo.setMax(read(m, dataType, TopicEnum.MAX));
								rollupVo.setMin(read(m, dataType, TopicEnum.MIN));
								rollupVo.setSum(read(m, dataType, TopicEnum.SUM));
								rollupVo.setAverage(read(m, dataType, TopicEnum.AVERAGE));
								rollupVo.setP5(read(m, dataType, TopicEnum.P5));
								rollupVo.setP95(read(m, dataType, TopicEnum.P95));
							}

						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
			));
			if (!result) {
				return null;
			}
			//logger.debug("rollUpVo:{}", rollupVo.toString());
			return rollupVo;
		}

		/**
		 * 根据不同的类型读取Max,Min等值
		 *
		 * @param m         WireIn
		 * @param dataType  数据类型
		 * @param topicEnum key
		 * @return 值
		 */
		private static Number read(WireIn m, String dataType, TopicEnum topicEnum) {
			if (DataTypeEnum.DOUBLE.getType().equals(dataType)) {
				return m.read(topicEnum.getTopic()).float64();
			}
			if (DataTypeEnum.LONG.getType().equals(dataType)) {
				return m.read(topicEnum.getTopic()).int64();
			}
			return null;
		}
}
