package com.techsure.strange.chronicle;

import com.techsure.strange.hazelcast.assist.DataTypeEnum;
import com.techsure.strange.hazelcast.assist.RollupVo;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import net.openhft.chronicle.wire.WireIn;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/30 星期四 12:58
 */
public class ReadTest {
	private static Logger logger = LoggerFactory.getLogger(ReadTest.class);

	@Test
	public void testWrite() throws InterruptedException {
		String path = "E:\\Data\\project\\ts-metric\\queue\\5minute\\17-te\\17";
		SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).rollCycle(RollCycleEnum.FIVE_MINUTE).build();
		//ExcerptTailer tailer = queue.createTailer();
		ExcerptAppender appender = queue.acquireAppender();
		ExcerptTailer tailer = queue.createTailer();
		RollupVo rollupVo;
		/*Long index = 0L;
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			appender.writeDocument(w -> w.write(TopicEnum.ROLLUP.getTopic()).marshallable(
					m -> {
						m.write("tenant").text("tenant" + finalI).
								write(TopicEnum.METRIC_ID.getTopic()).text("metricId" + finalI)
								.write(TopicEnum.DATA_TYPE.getTopic()).text(DataTypeEnum.DOUBLE.getType())
								.write(TopicEnum.TTL.getTopic()).int32(111)
								.write(TopicEnum.TIME.getTopic()).int64(System.currentTimeMillis())
								.write(TopicEnum.NUM.getTopic()).int32(finalI);
					}
			));
			logger.info("appender index:{}", appender.lastIndexAppended());
			if (i == 5) {
				index = appender.lastIndexAppended();
			}
		}
		logger.info(String.valueOf(index));*/
		Integer count = 0;
		logger.info("tailer index:{}", tailer.index());
		while ((rollupVo = read(tailer)) != null) {
			count++;
			if(rollupVo.getMetricId().equals("444584")){
				logger.info(rollupVo.toString());
			}
		}
		logger.info(String.valueOf(count));
	}

	@Test
	public void testRead() throws InterruptedException {
		String path = "queue";
		SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).rollCycle(RollCycleEnum.FIVE_MINUTE).build();
		ExcerptTailer tailer = queue.createTailer();
		RollupVo rollupVo;
		Integer count = 0;
		Set<String> set = new HashSet<>();
		while ((rollupVo = read(tailer)) != null) {
			set.add(rollupVo.getMetricId());
			count++;
			logger.info(String.valueOf(tailer.index()));
			//TimeUnit.SECONDS.sleep(5L);
		}
		logger.info("size:{}", set.size());
		logger.info("num:{}", count);
	}

	public RollupVo read(ExcerptTailer tailer) {
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
	private Number read(WireIn m, String dataType, TopicEnum topicEnum) {
		if (DataTypeEnum.DOUBLE.getType().equals(dataType)) {
			return m.read(topicEnum.getTopic()).float64();
		}
		if (DataTypeEnum.LONG.getType().equals(dataType)) {
			return m.read(topicEnum.getTopic()).int64();
		}
		return null;
	}
}
