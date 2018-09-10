package com.techsure.strange.netty;

import com.techsure.strange.chronicle.TopicEnum;
import com.techsure.strange.hazelcast.assist.DataTypeEnum;
import com.techsure.strange.hazelcast.assist.RollupVo;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.RollCycles;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import net.openhft.chronicle.wire.WireOut;

/**
 * @author zhoujian
 * @Date 2018/9/1 星期六 11:04
 */
public class ChronicleWriterUtil {
	private static ChronicleQueue queue;
	private static ExcerptAppender appender;
	public static void write(TransportVo transportVo) {
		transportVo.getRollupVoList().forEach(rollupVo -> {
			ExcerptAppender appender = getAppender();
			write(appender, rollupVo);
		});
	}

	public static ExcerptAppender getAppender(){
		if(queue == null){
			queue = SingleChronicleQueueBuilder.binary("queue").rollCycle(RollCycles.MINUTELY).build();
			appender = queue.acquireAppender();
		}
		return appender;
	}

	public static void write(ExcerptAppender appender, RollupVo rollupVo) {
		appender.writeDocument(w -> w.write(TopicEnum.ROLLUP.getTopic()).marshallable(
				m -> {
					m.write(TopicEnum.TENANT.getTopic()).text(rollupVo.getTenant())
							.write(TopicEnum.TABLE.getTopic()).text(rollupVo.getTable())
							.write(TopicEnum.METRIC_ID.getTopic()).text(rollupVo.getMetricId())
							.write(TopicEnum.DATA_TYPE.getTopic()).text(rollupVo.getDataType())
							.write(TopicEnum.TTL.getTopic()).int32(rollupVo.getTtl())
							.write(TopicEnum.TIME.getTopic()).int64(rollupVo.getTime())
							.write(TopicEnum.NUM.getTopic()).int32(rollupVo.getNum());

					String dataType = rollupVo.getDataType();
					if (!DataTypeEnum.STRING.getType().equals(dataType)) {
						write(m, dataType, TopicEnum.MAX, rollupVo.getMax());
						write(m, dataType, TopicEnum.MIN, rollupVo.getMin());
						write(m, dataType, TopicEnum.AVERAGE, rollupVo.getAverage());
						write(m, dataType, TopicEnum.SUM, rollupVo.getSum());
						write(m, dataType, TopicEnum.P5, rollupVo.getP5());
						write(m, dataType, TopicEnum.P95, rollupVo.getP95());
					}
				}
		));
	}

	private static <T> void write(WireOut m, String dataType, TopicEnum topicEnum, T t) {
		if (DataTypeEnum.DOUBLE.getType().equals(dataType)) {
			m.write(topicEnum.getTopic()).float64((Double) t);
		}
		if (DataTypeEnum.LONG.getType().equals(dataType)) {
			m.write(topicEnum.getTopic()).int64((Long) t);
		}
	}

}
