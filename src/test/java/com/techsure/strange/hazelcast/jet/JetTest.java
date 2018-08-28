package com.techsure.strange.hazelcast.jet;

import com.hazelcast.core.IMap;
import com.hazelcast.jet.IMapJet;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Job;
import com.hazelcast.jet.aggregate.AggregateOperations;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.config.ProcessingGuarantee;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;
import com.hazelcast.jet.pipeline.WindowDefinition;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static com.hazelcast.jet.Util.mapEventNewValue;
import static com.hazelcast.jet.Util.mapPutEvents;
import static com.hazelcast.jet.pipeline.JournalInitialPosition.START_FROM_CURRENT;
import static com.hazelcast.jet.pipeline.JournalInitialPosition.START_FROM_OLDEST;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author zhoujian
 * @Date 2018/8/20 21:06
 */
public class JetTest {
	private static final Logger logger = LoggerFactory.getLogger(JetTest.class);
	private static final String IN_MAP_NAME = "inMap";

	@Test
	public void testMapSet() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		JobConfig jobConfig = new JobConfig();
		jobConfig.setName("Test");
		jet.newJob(buildPipeline(), jobConfig);
		lock.unlock();


		while (true) {
			TimeUnit.SECONDS.sleep(10L);
		}
	}

	@Test
	public void testMapSetB() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		Job job = jet.getJob("Test");
		if (job != null) {
			job.restart();
		}
		lock.unlock();

		Long begin = System.currentTimeMillis();
		Long time = 0L;
		for (int j = 0; j < 100; j++) {
			for (int i = 0; i < 10; i++) {
				MetricVo metricVo = new MetricVo();
				metricVo.setMetricId((long) i);
				metricVo.setMetricValue((long) 1);
				metricVo.setGenerateTime(time + j * 1000);
				map.set((long) i, metricVo);
			}

		}
		logger.info("consume:{}", System.currentTimeMillis() - begin);

		logger.info("set consume time:{}", System.currentTimeMillis() - begin);
		while (true) {
			TimeUnit.SECONDS.sleep(2L);
		}
	}

	@Test
	public void testSnapShot() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		jet.newJob(buildPipeline());
		lock.unlock();

		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
		//BlockingQueue queue = new LinkedBlockingDeque();

		try {
			Long begin = System.currentTimeMillis();
			Long time = 0L;
			for (int i = 0; i < 100; i++) {
				MetricVo metricVo = new MetricVo();
				metricVo.setMetricId(111L);
				metricVo.setMetricValue((long) i);
				metricVo.setGenerateTime(time + i * 1000);
				map.set(metricVo.getMetricId(), metricVo);
				//logger.info("timestamp:{}", DateFormatUtils.format(metricVo.getGenerateTime(), "yyyy-MM-dd HH:mm:ss"));
			}


			logger.info("consume time:{}", System.currentTimeMillis() - begin);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		while (true) {
			/*MetricVo metricVo = (MetricVo) queue.take();
			map.set(metricVo.getMetricId(), metricVo);*/
			TimeUnit.MILLISECONDS.sleep(2);
		}
	}

	@Test
	public void testSnapshot() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		JobConfig jobConfig = new JobConfig();
		jobConfig.setProcessingGuarantee(ProcessingGuarantee.AT_LEAST_ONCE);
		jobConfig.setSnapshotIntervalMillis(SECONDS.toMillis(20));
		jobConfig.setName("Test");
		Job job = jet.newJob(buildPipeline(), jobConfig);
		lock.unlock();

		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
		//BlockingQueue queue = new LinkedBlockingDeque();

		while (true) {
			/*MetricVo metricVo = (MetricVo) queue.take();
			map.set(metricVo.getMetricId(), metricVo);*/
			TimeUnit.MILLISECONDS.sleep(2);
		}

	}

	@Test
	public void testSnapshotB() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		Job job = jet.getJob("Test");
		if (job != null) {
			job.restart();
		}
		lock.unlock();

		while (true) {
			TimeUnit.SECONDS.sleep(1L);
		}
	}


	private static JetConfig getJetConfig() {
		JetConfig cfg = new JetConfig();
		cfg.getHazelcastConfig()
				.getMapEventJournalConfig(IN_MAP_NAME)
				.setEnabled(true)
				.setCapacity(30000000)
				.setTimeToLiveSeconds(0)

		;
		return cfg;
	}

	private static Pipeline buildPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<MetricVo, Long, MetricVo>mapJournal(IN_MAP_NAME, mapPutEvents(), mapEventNewValue(), START_FROM_OLDEST))
				.addTimestamps(MetricVo::getGenerateTime, 0)
				.window(WindowDefinition.tumbling(30000))
				.groupingKey(MetricVo::getMetricId)
				//.aggregate(caculateMetric(metricVo -> metricVo.getMetricValue()))
				//.aggregate(caculateMetric(MetricVo::getMetricValue))
				//.aggregate(caculateMetric(MetricVo::getMetricValue))
				.aggregate(AggregateOperations.summingDouble(MetricVo::getMetricValue))
				//.drainTo(Sinks.map(OUT_MAP_NAME));
				//.drainTo(Sinks.list(OUT_MAP_NAME));
				//.drainTo(Sinks.logger((TimestampedEntry<Long, MetricResultVo> t) -> t.toString()));
				//.drainTo(Sinks.files("E:\\Data\\project\\ts-metric"));
				.drainTo(Sinks.logger());
		//.drainTo(getCustomSink());
		//.drainTo(Sinks.builder(DistributedFunction.identity()).onReceiveFn((DistributedBiConsumer<JetInstance, Object>) (jetInstance, o) -> System.out.println(o)).build());
		return p;

	}
}
