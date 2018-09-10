package com.techsure.strange.hazelcast.jet;

import com.hazelcast.core.IList;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.aggregate.AggregateOperations;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sink;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;
import com.techsure.strange.chronicle.ReadUtil;
import com.techsure.strange.chronicle.RollCycleEnum;
import com.techsure.strange.hazelcast.assist.DataTypeEnum;
import com.techsure.strange.hazelcast.assist.JetJobUtils;
import com.techsure.strange.hazelcast.assist.RollupVo;
import net.openhft.chronicle.core.util.Time;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueue;
import net.openhft.chronicle.queue.impl.single.SingleChronicleQueueBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/29 星期三 20:00
 */
public class ListTest {
	private static final Logger logger = LoggerFactory.getLogger(ListTest.class);

	private  JetInstance jet;


	@Test
	public void testAn() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();

		while (true){
			TimeUnit.SECONDS.sleep(3L);
		}

	}

	@Test
	public void testHourJob(){
		doRoll("queue",0L);
	}

	void doRoll(String path, Long time) {
		jet = Jet.newJetInstance();
		SingleChronicleQueue queue = SingleChronicleQueueBuilder.binary(path).rollCycle(RollCycleEnum.FIVE_MINUTE).build();
		ExcerptTailer tailer = queue.createTailer();
		RollupVo rollupVo;
		IList<RollupVo> list = jet.getList("fiveMinuteDataList");
		while ((rollupVo = ReadUtil.read(tailer)) != null) {
			list.add(rollupVo);
		}
		logger.info("one hour roll list size :{}",list.size());
		createJob(time);
		list.clear();
	}


	public void createJob(Long time) {
		JobConfig jobConfig = new JobConfig();
		jobConfig.setName("oneHourJob");
		jet.newJob(buildPipeline(time), jobConfig).join();
	}

	Pipeline buildPipeline(Long time) {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<RollupVo>list("fiveMinuteDataList"))
				.groupingKey(RollupVo::getMetricId)
				.aggregate(JetJobUtils.calculateMetric())
				.drainTo(createListCustomSink(time));
		return p;
	}

	Sink<Map.Entry<String, RollupVo>> createListCustomSink(Long time) {
		return Sinks
				.<Logger, Map.Entry<String, RollupVo>>builder(jetInstance -> LoggerFactory.getLogger(JetJobUtils.class))
				.onReceiveFn((Logger logger, Map.Entry<String, RollupVo> result) -> {
					//result为TimestampEntry,包含key、time、value，value的值为RollUpVo
					logger.debug(result.toString());
					result.getValue().setTime(time);
					DataQueue.put(result.getValue());
				})
				.build();
	}

	@Test
	public void testList() {
		JetInstance jet = Jet.newJetInstance();
		IList<Integer> inputList = jet.getList("inputList");
		for (int i = 0; i < 10; i++) {
			inputList.add(i);
		}

		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<Integer>list("inputList"))
				.map(i -> "item" + i)
				.drainTo(Sinks.list("resultList"));

		jet.newJob(p).join();
		inputList.clear();

		IList<String> resultList = jet.getList("resultList");
		System.out.println("Results: " + new ArrayList<>(resultList));
	}

	@Test
	public void testListJet() {
		JetInstance jet = Jet.newJetInstance();
		IList<RollupVo> list = jet.getList("test");
		for (int i = 0; i < 100; i++) {
			RollupVo rollupVo = new RollupVo();
			rollupVo.setMetricId(String.valueOf(i));
			rollupVo.setP95(Double.valueOf(i));
			list.add(rollupVo);
		}

		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<RollupVo>list("test"))
				.groupingKey(e -> e.getMetricId())
				.aggregate(AggregateOperations.summingDouble(e -> (double) e.getValue())).drainTo(Sinks.logger());
		jet.newJob(p).join();
	}

	@Test
	public void TestWindowSession() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		IList<RollupVo> list = jet.getList("test");

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				RollupVo rollupVo = new RollupVo();
				rollupVo.setMetricId(String.valueOf(i));
				rollupVo.setP95(Double.valueOf(i + j));
				rollupVo.setTime((long) j * 1000);
				rollupVo.setDataType(DataTypeEnum.DOUBLE.getType());
				list.add(rollupVo);
			}
		}
		jet.newJob(buildPipeline()).join();
		while (true) {
			TimeUnit.SECONDS.sleep(1L);
		}
	}

	//private BlockingQueue<RollupVo> queue;

	private DataQueue dataQueue;

	@Test
	public void testJob() throws InterruptedException {
		createJob(1535630400000L);
	}

	/*public void createJob(Long time) throws InterruptedException {
		dataQueue = new DataQueue();
		JetInstance jet = Jet.newJetInstance();
		IList<RollupVo> list = jet.getList("test");

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				RollupVo rollupVo = new RollupVo();
				rollupVo.setMetricId(String.valueOf(i));
				rollupVo.setP95(Double.valueOf(i + j));
				rollupVo.setTime((long) j * 1000);
				rollupVo.setDataType(DataTypeEnum.DOUBLE.getType());
				list.add(rollupVo);
			}
		}

		JobConfig jobConfig = new JobConfig();
		jobConfig.setName("oneHourJob");
		jet.newJob(buildPipeline(time), jobConfig).join();
		while (true) {
			RollupVo rollupVo = DataQueue.take();
			logger.info(rollupVo.toString());
		}
	}*/
/*
	Pipeline buildPipeline(Long time) {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<RollupVo>list("test"))
				.groupingKey(RollupVo::getMetricId)
				.aggregate(JetJobUtils.calculateMetric())
				.drainTo(createListCustomSink(time));
		//.drainTo(Sinks.logger());
		return p;
	}*/

	/*Sink<Map.Entry<String, RollupVo>> createListCustomSink(Long time) {
		return Sinks
				.<Logger, Map.Entry<String, RollupVo>>builder(jetInstance -> LoggerFactory.getLogger(ListTest.class))
				.onReceiveFn((Logger logger, Map.Entry<String, RollupVo> result) -> {
					//result为TimestampEntry,包含key、time、value，value的值为RollUpVo
					logger.debug("class:{}", dataQueue.getClass());
					result.getValue().setTime(time);
					logger.debug(result.toString());
					dataQueue.put(result.getValue());

				})
				.build();
	}*/

	private Pipeline buildPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<RollupVo>list("test"))
				//.addTimestamps(RollupVo::getTime, 0)
				//.window(WindowDefinition.session(0))
				.groupingKey(RollupVo::getMetricId)
				.aggregate(JetJobUtils.calculateMetric())
				.drainTo(Sinks.logger());
		return p;
	}

}
