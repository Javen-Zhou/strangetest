package com.techsure.strange.hazelcast.jet;

import com.hazelcast.config.EventJournalConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.jet.IMapJet;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Job;
import com.hazelcast.jet.aggregate.AggregateOperation;
import com.hazelcast.jet.aggregate.AggregateOperation1;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.config.ProcessingGuarantee;
import com.hazelcast.jet.datamodel.TimestampedEntry;
import com.hazelcast.jet.function.DistributedBiConsumer;
import com.hazelcast.jet.function.DistributedConsumer;
import com.hazelcast.jet.function.DistributedFunction;
import com.hazelcast.jet.function.DistributedToDoubleFunction;
import com.hazelcast.jet.pipeline.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static com.hazelcast.jet.Util.mapEventNewValue;
import static com.hazelcast.jet.Util.mapPutEvents;
import static com.hazelcast.jet.pipeline.JournalInitialPosition.START_FROM_CURRENT;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author zhoujian
 * @Date 2018/7/13 16:06
 */
public class TestJetMapJournal {

	private static final Logger logger = LoggerFactory.getLogger(TestJetMapJournal.class);

	private static final String IN_MAP_NAME = "inMap";
	private static final String OUT_MAP_NAME = "outMap";


	@Test
	public void testGuaranteeA() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		Job job = jet.getJob("Test");
		if (job == null) {
			JobConfig jobConfig = new JobConfig();
			jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
			jobConfig.setSnapshotIntervalMillis(SECONDS.toMillis(2));
			jobConfig.setName("Test");
			jet.newJob(buildPipeline(), jobConfig);
		} else {
			job.restart();
		}
		lock.unlock();

		while(true){
			TimeUnit.SECONDS.sleep(3L);
		}
	}

	@Test
	public void testGuaranteeB() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		Job job = jet.getJob("Test");
		if (job == null) {
			JobConfig jobConfig = new JobConfig();
			jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
			jobConfig.setSnapshotIntervalMillis(SECONDS.toMillis(2));
			jobConfig.setName("Test");
			jet.newJob(buildPipeline(), jobConfig);
		} else {
			job.restart();
		}
		lock.unlock();

		while(true){
			TimeUnit.SECONDS.sleep(3L);
		}
	}

	@Test
	public void testSetData() throws InterruptedException {
		Long times = 1533713160000L;
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
		for (int k = 0; k < 5; k++) {
			times = times + k * 5000L;
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 5; j++) {
					MetricVo metricVo = new MetricVo();
					metricVo.setMetricId((long) i);
					metricVo.setMetricValue((long) i + j);
					metricVo.setGenerateTime(times + j * 1000);
					logger.info("id:{},time:{},value:{}", metricVo.getMetricId(), DateFormatUtils.format(metricVo.getGenerateTime(), "yyyy-MM-dd HH:mm:ss"), metricVo.getMetricValue());
					map.set(metricVo.getMetricId(), metricVo);
				}
				//TimeUnit.SECONDS.sleep(5L);

			}
		}
		SECONDS.sleep(5L);
		jet.shutdown();

	}

	@Test
	public void testHandleDataA() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		Job job = jet.getJob("Test");
		if (job == null) {
			JobConfig jobConfig = new JobConfig();
			jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
			jobConfig.setSnapshotIntervalMillis(SECONDS.toMillis(2));
			jobConfig.setName("Test");
			jet.newJob(buildPipeline(), jobConfig).join();
		} else {
			job.restart();
		}
		lock.unlock();
		Thread thread = new Thread(()->{
			try {
				SECONDS.sleep(20L);
				IMapJet<Long,MetricVo> map = jet.getMap(IN_MAP_NAME);

				HazelcastInstance instance = jet.getHazelcastInstance();
				BlockingQueue<MetricVo> queue = instance.getQueue("Queue");
				Long begin = System.currentTimeMillis();
				Long end = System.currentTimeMillis();
				while(true){
					MetricVo metricVo = queue.take();
					map.set(metricVo.getMetricId(),metricVo);
					end = System.currentTimeMillis();
					logger.info("时间差:{}", end - begin);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});
		thread.start();

		while (true){
			SECONDS.sleep(2L);
		}
	}

	@Test
	public void testHandleDataAA() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		Job job = jet.getJob("Test");
		if (job == null) {
			JobConfig jobConfig = new JobConfig();
			jobConfig.setName("Test");
			jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
			jobConfig.setSnapshotIntervalMillis(SECONDS.toMillis(2));
			jet.newJob(buildPipeline(), jobConfig);
		} else {
			job.restart();
		}
		lock.unlock();
		Thread thread = new Thread(()->{
			try {
				SECONDS.sleep(20L);
				IMapJet<Long,MetricVo> map = jet.getMap(IN_MAP_NAME);

				HazelcastInstance instance = jet.getHazelcastInstance();
				BlockingQueue<MetricVo> queue = instance.getQueue("Queue");
				Long begin = System.currentTimeMillis();
				Long end = System.currentTimeMillis();
				while(true){
					MetricVo metricVo = queue.take();
					map.set(metricVo.getMetricId(),metricVo);
					end = System.currentTimeMillis();
					logger.info("时间差:{}", end - begin);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});
		thread.start();

		while (true){
			SECONDS.sleep(2L);
		}
	}

	@Test
	public void testHandleDataB() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		Job job = jet.getJob("Test");
		if (job == null) {
			JobConfig jobConfig = new JobConfig();
			jobConfig.setName("Test");
			jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
			jobConfig.setSnapshotIntervalMillis(SECONDS.toMillis(2));
			jet.newJob(buildPipeline(), jobConfig);
		} else {
			job.restart();
		}
		lock.unlock();
		//TimeUnit.SECONDS.sleep(10L);

		Long times = 1533713160000L;

		HazelcastInstance instance = jet.getHazelcastInstance();
		BlockingQueue<MetricVo> queue = instance.getQueue("Queue");

		for (int k = 0; k < 10; k++) {
			times = times + k * 5000L;
			for (int i = 0; i < 10000; i++) {
				for (int j = 0; j < 5; j++) {
					MetricVo metricVo = new MetricVo();
					metricVo.setMetricId((long) i);
					metricVo.setMetricValue((long) i + j);
					metricVo.setGenerateTime(times + j * 1000);
					logger.info("id:{},time:{},value:{}", metricVo.getMetricId(), DateFormatUtils.format(metricVo.getGenerateTime(), "yyyy-MM-dd HH:mm:ss"), metricVo.getMetricValue());
					//map.set(metricVo.getMetricId(), metricVo);
					queue.put(metricVo);
				}
				//TimeUnit.SECONDS.sleep(5L);

			}
		}

		while (true) {
			SECONDS.sleep(10L);
		}
	}

	@Test
	public void testClientAndServer() throws InterruptedException {

		JetInstance jetInstance1 = createNode();
		JetInstance jetInstance2 = createNode();

		JetInstance client = Jet.newJetClient();
		JobConfig config = new JobConfig();
		config.setProcessingGuarantee(ProcessingGuarantee.NONE);
		config.setSnapshotIntervalMillis(5000);

		Job job = client.newJob(buildPipeline(), config);


		IMapJet<Long, MetricVo> map = jetInstance1.getMap(IN_MAP_NAME);
		/*for (int i = 0; i < 1000; i++) {
			MetricVo metricVo = new MetricVo();
			metricVo.setMetricId(3333L);
			metricVo.setMetricValue(i + 100L);
			metricVo.setGenerateTime(System.currentTimeMillis());
			map.set(metricVo.getMetricId(), metricVo);
			System.out.println(DateFormatUtils.format(metricVo.getGenerateTime(), "yyyy-MM-dd HH:mm:ss"));
			TimeUnit.SECONDS.sleep(3L);
		}*/
		Long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			time += i * 1000;
			MetricVo metricVo = new MetricVo();
			metricVo.setMetricId((long) i);
			metricVo.setMetricValue((long) i);
			metricVo.setGenerateTime(time);
			map.set(metricVo.getMetricId(), metricVo);
		}
		SECONDS.sleep(5L);
		jetInstance2.shutdown();
	}

	@Test
	public void startJetClient() throws InterruptedException {
		JetInstance client = Jet.newJetClient();
		JobConfig config = new JobConfig();
		config.setProcessingGuarantee(ProcessingGuarantee.NONE);
		config.setSnapshotIntervalMillis(5000);
		config.setAutoRestartOnMemberFailure(true);
		Job job = client.newJob(buildPipeline(), config);

		while (true) {
			SECONDS.sleep(3L);
		}
	}

	@Test
	public void satrtJetServer() throws InterruptedException {
		JetInstance jet = createNode();
		Job job = jet.getJob("Test");
		if (job == null) {
			JetInstance client = Jet.newJetClient();
			JobConfig config = new JobConfig();
			config.setProcessingGuarantee(ProcessingGuarantee.NONE);
			config.setSnapshotIntervalMillis(5000);
			config.setAutoRestartOnMemberFailure(true);
			config.setName("Test");
			job = client.newJob(buildPipeline(), config);
		} else {
			job.restart();
		}
		//TimeUnit.SECONDS.sleep(20L);
		//putData(jet);

		while (true) {
			SECONDS.sleep(3L);
		}
	}

	@Test
	public void putData() throws InterruptedException {
		JetInstance jet = createNode();
		Job job = jet.getJob("Test");
		if (job == null) {
			JetInstance client = Jet.newJetClient();
			JobConfig config = new JobConfig();
			config.setProcessingGuarantee(ProcessingGuarantee.NONE);
			config.setSnapshotIntervalMillis(5000);
			config.setAutoRestartOnMemberFailure(true);
			config.setName("Test");
			job = client.newJob(buildPipeline(), config);
		} else {
			job.restart();
		}
		SECONDS.sleep(10L);
		System.out.println("Start put Data");
		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
		/*for (int i = 0; i < 1000; i++) {
			MetricVo metricVo = new MetricVo();
			metricVo.setMetricId(3333L);
			metricVo.setMetricValue(i + 100L);
			metricVo.setGenerateTime(System.currentTimeMillis());
			map.set(metricVo.getMetricId(), metricVo);
			System.out.println(DateFormatUtils.format(metricVo.getGenerateTime(), "yyyy-MM-dd HH:mm:ss"));
			TimeUnit.SECONDS.sleep(3L);
		}*/
		Long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			time += i * 1000;
			MetricVo metricVo = new MetricVo();
			metricVo.setMetricId((long) i);
			metricVo.setMetricValue((long) i);
			metricVo.setGenerateTime(time);
			map.set(metricVo.getMetricId(), metricVo);
		}
		SECONDS.sleep(5L);

		while (true) {
			SECONDS.sleep(3L);
		}
	}

	private void putData(JetInstance jet) throws InterruptedException {
		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
		/*for (int i = 0; i < 1000; i++) {
			MetricVo metricVo = new MetricVo();
			metricVo.setMetricId(3333L);
			metricVo.setMetricValue(i + 100L);
			metricVo.setGenerateTime(System.currentTimeMillis());
			map.set(metricVo.getMetricId(), metricVo);
			System.out.println(DateFormatUtils.format(metricVo.getGenerateTime(), "yyyy-MM-dd HH:mm:ss"));
			TimeUnit.SECONDS.sleep(3L);
		}*/
		Long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			time += i * 1000;
			MetricVo metricVo = new MetricVo();
			metricVo.setMetricId((long) i);
			metricVo.setMetricValue((long) i);
			metricVo.setGenerateTime(time);
			map.set(metricVo.getMetricId(), metricVo);
		}
		SECONDS.sleep(5L);

		while (true) {
			SECONDS.sleep(3L);
		}
	}


	@Test
	public void startMultiHazelCast() throws InterruptedException {

		JetInstance jet = Jet.newJetInstance(getJetConfig());
		Job job = jet.getJob("Test");
		if (job == null) {
			JetInstance client = Jet.newJetClient();
			JobConfig config = new JobConfig();
			config.setProcessingGuarantee(ProcessingGuarantee.NONE);
			config.setSnapshotIntervalMillis(5000);
			config.setAutoRestartOnMemberFailure(true);
			config.setName("Test");
			job = client.newJob(buildPipeline(), config);
		} else {
			job.restart();
		}

		while (true) {
			SECONDS.sleep(3L);
		}
	}


	@Test
	public void startMultiHazelCast2() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance(getJetConfig());
		Job job = jet.getJob("Test");
		if (job == null) {
			JetInstance client = Jet.newJetClient();
			JobConfig config = new JobConfig();
			config.setProcessingGuarantee(ProcessingGuarantee.NONE);
			config.setSnapshotIntervalMillis(5000);
			config.setAutoRestartOnMemberFailure(true);
			config.setName("Test");
			job = client.newJob(buildPipeline(), config);
		} else {
			job.restart();
		}

		SECONDS.sleep(20L);


		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);

		Long time = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			time += i * 1000;
			MetricVo metricVo = new MetricVo();
			metricVo.setMetricId((long) i);
			metricVo.setMetricValue((long) i);
			metricVo.setGenerateTime(time);
			map.set(metricVo.getMetricId(), metricVo);
		}
		SECONDS.sleep(5L);

		while (true) {
			SECONDS.sleep(3L);
		}
	}

	private void shutdownUnnecessaryJobs(List<Job> jobList) {
		for (int i = 1; i < jobList.size(); i++) {
			jobList.get(i).cancel();
		}
	}

	@Test
	public void testJobs() {
		JetInstance jet = Jet.newJetInstance(getJetConfig());
		List<Job> jobList = jet.getJobs();
		jobList.forEach(e -> System.out.println(e.getName() + ";" + e.getConfig()));
		Job job = jet.getJob("Test");
		if (job == null) {
			System.out.println("you");
		}
	}

	@Test
	public void testJob() {
		JetConfig cfg = new JetConfig();
		cfg.getHazelcastConfig()
				.getMapEventJournalConfig("fiveMinuteMap")
				.setEnabled(true)
				.setCapacity(1000)
				.setTimeToLiveSeconds(0);
		JetInstance jet = Jet.newJetInstance(cfg);
		List<Job> jobList = jet.getJobs();

		System.out.println("you");
	}

	@Test
	public void test1() throws InterruptedException {
		JetConfig cfg = getJetConfig();
		JetInstance jet = Jet.newJetInstance(cfg);
		JobConfig jobConfig = new JobConfig();
		jobConfig.setName("Test");
		Job job = jet.newJob(buildPipeline(), jobConfig);


		while (true) {
			SECONDS.sleep(3L);
		}
	}

	@Test
	public void test2() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance(getJetConfig());

		try {
			IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
			for (int i = 0; i < 11; i++) {
				MetricVo metricVo = new MetricVo();
				metricVo.setMetricId(111L);
				metricVo.setMetricValue(i + 100L);
				metricVo.setGenerateTime(System.currentTimeMillis());
				map.set(metricVo.getMetricId(), metricVo);
				System.out.println(DateFormatUtils.format(metricVo.getGenerateTime(), "yyyy-MM-dd HH:mm:ss"));
				SECONDS.sleep(3L);
			}
			SECONDS.sleep(5L);
		} finally {
			jet.shutdown();
		}
	}

	@Test
	public void test22() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			JetInstance jet = Jet.newJetInstance(getJetConfig());
			try {
				IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
				MetricVo metricVo = new MetricVo();
				metricVo.setMetricId(111L);
				metricVo.setMetricValue(i + 100L);
				metricVo.setGenerateTime(System.currentTimeMillis());
				map.set(metricVo.getMetricId(), metricVo);
			} finally {
				jet.shutdown();
			}
			SECONDS.sleep(3L);

		}
	}

	@Test
	public void test3() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance(getJetConfig());
		try {
			Integer count = 0;
			while (true) {
				System.out.println("test" + count + ":");
				IMapJet map = jet.getMap(OUT_MAP_NAME);
				map.forEach((k, v) -> System.out.println(v));
				System.out.println("test end");
				SECONDS.sleep(2L);
			}
		} finally {
			jet.shutdown();
		}
	}

	@Test
	public void test4() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance(getJetConfig());
		try {
			Integer count = 0;
			while (true) {
				System.out.println("test" + count + ":");
				IList list = jet.getList(OUT_MAP_NAME);
				list.forEach(System.out::println);
				System.out.println("test end");
				SECONDS.sleep(2L);
			}
		} finally {
			jet.shutdown();
		}
	}

	private static Pipeline buildPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<MetricVo, Long, MetricVo>mapJournal(IN_MAP_NAME, mapPutEvents(), mapEventNewValue(), START_FROM_CURRENT))
				.addTimestamps(MetricVo::getGenerateTime, 0)
				.window(WindowDefinition.tumbling(5000))
				.groupingKey(MetricVo::getMetricId)
				//.aggregate(caculateMetric(metricVo -> metricVo.getMetricValue()))
				//.aggregate(caculateMetric(MetricVo::getMetricValue))
				.aggregate(caculateMetric(MetricVo::getMetricValue))
				//.aggregate(AggregateOperations.averagingDouble(MetricVo::getMetricValue))
				//.drainTo(Sinks.map(OUT_MAP_NAME));
				//.drainTo(Sinks.list(OUT_MAP_NAME));
				//.drainTo(Sinks.logger((TimestampedEntry<Long, MetricResultVo> t) -> t.toString()));
				.drainTo(Sinks.files("F:\\Techsure\\ts-metric\\data"));
		//.drainTo(Sinks.logger());
		//.drainTo(getCustomSink());
		//.drainTo(Sinks.builder(DistributedFunction.identity()).onReceiveFn((DistributedBiConsumer<JetInstance, Object>) (jetInstance, o) -> System.out.println(o)).build());
		return p;

	}

	private static JetConfig getJetConfig() {
		JetConfig cfg = new JetConfig();
		cfg.getHazelcastConfig()
				.getMapEventJournalConfig(IN_MAP_NAME)
				.setEnabled(true)
				.setCapacity(100000)
				.setTimeToLiveSeconds(30)

		;
		return cfg;
	}

	private static JetInstance createNode() {
		JetConfig config = new JetConfig();

		EventJournalConfig journalConfig = new EventJournalConfig()
				.setMapName(IN_MAP_NAME)
				.setCapacity(10000)
				.setEnabled(true);

		MapConfig mapConfig = new MapConfig(IN_MAP_NAME)
				.setBackupCount(1);
		config.getHazelcastConfig().addMapConfig(mapConfig);
		config.getHazelcastConfig().addEventJournalConfig(journalConfig);

		return Jet.newJetInstance(config);
	}

	private static Sink<TimestampedEntry<Long, MetricResultVo>> getCustomSink() {
		return Sinks.<Logger, TimestampedEntry<Long, MetricResultVo>>builder(new DistributedFunction<JetInstance, Logger>() {
			@Override
			public Logger apply(JetInstance jetInstance) {
				System.out.println("initMap");
				return LoggerFactory.getLogger(TestJetMapJournal.class);
			}
		}).onReceiveFn(new DistributedBiConsumer<Logger, TimestampedEntry<Long, MetricResultVo>>() {
			@Override
			public void accept(Logger logger, TimestampedEntry<Long, MetricResultVo> o2) {
				/*List<TimestampedEntry> list = (List<TimestampedEntry>) o.get("111");
				if(list == null){
					list = new ArrayList<>();
				}
				list.add(o2);*/
				logger.info(o2.toString());
				System.out.println(o2.getKey() + ":add :");

			}
		}).destroyFn(new DistributedConsumer<Logger>() {
			@Override
			public void accept(Logger o) {
				System.out.println("end");
			}
		}).build();
	}

	private static <T> AggregateOperation1<T, MetricAccumulator, MetricResultVo> caculateMetric( DistributedToDoubleFunction<? super T> getDoubleValueFn) {
		return AggregateOperation
				.withCreate(MetricAccumulator::new)
				//.andAccumulate((MetricAccumulator m, T item) -> m.accumulate(getDoubleValueFn.applyAsDouble(item)))
				.andAccumulate((MetricAccumulator metricAccumulator, T o) -> metricAccumulator.accumulate(getDoubleValueFn.applyAsDouble(o)))
				.andFinish(MetricAccumulator::finish);
	}
}

class MetricAccumulator implements Serializable {
	private MetricResultVo metricResultVo;
	private List<Double> metricValueList;

	public MetricAccumulator() {
		metricValueList = new ArrayList<>();
	}

	public MetricAccumulator accumulate(double value) {
		metricValueList.add(value);
		return this;
	}

	public MetricAccumulator combine(MetricAccumulator that) {
		StringBuilder sb = new StringBuilder();
		that.metricValueList.forEach(e -> sb.append(e).append(";"));
		//System.out.println("combine:" + sb.toString());
		return this;
	}

	public MetricAccumulator deduct(MetricAccumulator that) {
		StringBuilder sb = new StringBuilder();
		that.metricValueList.forEach(e -> sb.append(e).append(";"));
		//System.out.println("deduct:" + sb.toString());
		return this;
	}

	public MetricResultVo finish() {
		metricResultVo = new MetricResultVo();

		metricResultVo.setValueList(this.metricValueList);
		//System.out.println(metricResultVo.toString());
		return metricResultVo;
	}

}

class MetricResultVo implements Serializable {
	private Double minValue;
	private Double maxValue;
	private Double per5Value;
	private Double per50Value;
	private Double per95Value;
	private List<Double> valueList;
	private Integer listCount;

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public Double getPer5Value() {
		return per5Value;
	}

	public void setPer5Value(Double per5Value) {
		this.per5Value = per5Value;
	}

	public Double getPer50Value() {
		return per50Value;
	}

	public void setPer50Value(Double per50Value) {
		this.per50Value = per50Value;
	}

	public Double getPer95Value() {
		return per95Value;
	}

	public void setPer95Value(Double per95Value) {
		this.per95Value = per95Value;
	}

	public List<Double> getValueList() {
		return valueList;
	}

	public void setValueList(List<Double> valueList) {
		this.valueList = valueList;
	}

	public Integer getListCount() {
		return listCount;
	}

	public void setListCount(Integer listCount) {
		this.listCount = listCount;
	}

	@Override
	public String toString() {
		StringBuilder listSb = new StringBuilder();
		valueList.forEach(e -> listSb.append(e.doubleValue()).append(";"));
		return "count:" + valueList.size() + "   list:" + listSb.toString() + "";
	}
}

class MetricVo implements Serializable {
	private Long metricId;
	private Long metricValue;
	private Long generateTime;


	public Long getMetricId() {
		return metricId;
	}

	public void setMetricId(Long metricId) {
		this.metricId = metricId;
	}

	public Long getMetricValue() {
		return metricValue;
	}

	public void setMetricValue(Long metricValue) {
		this.metricValue = metricValue;
	}

	public Long getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(Long generateTime) {
		this.generateTime = generateTime;
	}
}
