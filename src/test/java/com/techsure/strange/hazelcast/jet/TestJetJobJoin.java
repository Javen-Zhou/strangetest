package com.techsure.strange.hazelcast.jet;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static com.hazelcast.jet.Util.mapEventNewValue;
import static com.hazelcast.jet.Util.mapPutEvents;
import static com.hazelcast.jet.pipeline.JournalInitialPosition.START_FROM_CURRENT;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author zhoujian
 * @Date 2018/8/15 17:23
 */
public class TestJetJobJoin {

	private static final Logger logger = LoggerFactory.getLogger(TestJetJobJoin.class);
	private static final String IN_MAP_NAME = "inMap";

	@Test
	public void testObject() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		JobConfig jobConfig = new JobConfig();
		jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
		jobConfig.setSnapshotIntervalMillis(HOURS.toMillis(2));
		jobConfig.setName("Test");
		jet.newJob(buildPipeline(), jobConfig);
		lock.unlock();

		BlockingQueue<List<MetricVo>> queue = jet.getHazelcastInstance().getQueue("TESTQUEUE");
		MetricVo metricVo = new MetricVo();
		metricVo.setMetricId(1L);
		metricVo.setMetricValue(100L);
		metricVo.setGenerateTime(0L);
		List<MetricVo> list = new ArrayList<>();
		list.add(metricVo);
		queue.put(list);

		metricVo.setGenerateTime(1000L);
		metricVo.setMetricValue(1000L);

		List<MetricVo> metricVoList = queue.take();
		logger.info("value:{},time:{}",metricVoList.get(0).getMetricValue(),metricVoList.get(0).getGenerateTime());
	}

	@Test
	public void testSet() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		JobConfig jobConfig = new JobConfig();
		jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
		jobConfig.setSnapshotIntervalMillis(HOURS.toMillis(2));
		jobConfig.setName("Test");
		jet.newJob(buildPipeline(), jobConfig);
		lock.unlock();

		//TimeUnit.SECONDS.sleep(10L);
		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
		BlockingQueue<MetricVo> queue = jet.getHazelcastInstance().getQueue("TESTQUEUE");
		Long times = 1533713160000L;
		Long begin = System.currentTimeMillis();
		logger.info("queue start:{}", begin);

		for (int k = 0; k < 1000; k++) {
			times = times + k * 5000L;
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 4; j++) {
					MetricVo metricVo = new MetricVo();
					metricVo.setMetricId((long) i);
					metricVo.setMetricValue((long) i + j);
					metricVo.setGenerateTime(times + j * 1000);
					queue.put(metricVo);
				}

			}
		}
		Long end = System.currentTimeMillis();
		logger.info("queue end:{}", end);
		logger.info("queue consume time:{}", end - begin);

		begin = System.currentTimeMillis();
		logger.info("map start:{}", begin);
		while (true) {
			if (queue.size() == 0) {
				end = System.currentTimeMillis();
				logger.info("map end:{}", end);
				logger.info("map consume:{}", end - begin);
				break;
			} else {
				List<MetricVo> list = new ArrayList<>();
				queue.drainTo(list, 10000);
				//queue.take();
				logger.info("list size:{}",String.valueOf(list.size()));
				for (MetricVo metricVo : list) {
					map.setAsync(metricVo.getMetricId(), metricVo);
					//map.set(metricVo.getMetricId(), metricVo);
				}
			}
			//map.setAsync(metricVo.getMetricId(),metricVo);
		}
	}

	@Test
	public void jobA() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		JobConfig jobConfig = new JobConfig();
		jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
		jobConfig.setSnapshotIntervalMillis(HOURS.toMillis(2));
		jobConfig.setName("Test");
		jet.newJob(buildPipeline(), jobConfig).join();
		lock.unlock();

		TimeUnit.SECONDS.sleep(10L);
		IMapJet<Long, MetricVo> map = jet.getMap(IN_MAP_NAME);
		Long times = 1533713160000L;
		for (int k = 0; k < 10; k++) {
			times = times + k * 5000L;
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 5; j++) {
					MetricVo metricVo = new MetricVo();
					metricVo.setMetricId((long) i);
					metricVo.setMetricValue((long) i + j);
					metricVo.setGenerateTime(times + j * 1000);
					//logger.info("id:{},time:{},value:{}", metricVo.getMetricId(), DateFormatUtils.format(metricVo.getGenerateTime(), "yyyy-MM-dd HH:mm:ss"), metricVo.getMetricValue());
					map.set(metricVo.getMetricId(), metricVo);
				}
				//TimeUnit.SECONDS.sleep(5L);

			}
		}

		while (true) {
			TimeUnit.SECONDS.sleep(5L);
		}
	}

	@Test
	public void jobB() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		Lock lock = jet.getHazelcastInstance().getLock("Lock");
		lock.lock();
		JobConfig jobConfig = new JobConfig();
		jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
		jobConfig.setSnapshotIntervalMillis(HOURS.toMillis(2));
		jobConfig.setName("Test");
		jet.newJob(buildPipeline(), jobConfig).join();
		lock.unlock();
		while (true) {
			List<Job> list = jet.getJobs();
			logger.info("job start");
			list.forEach(job -> logger.info("jobId={},jobName={},jobStatus={}", job.getId(), job.getName(), job.getStatus()));
			logger.info("job end");
			TimeUnit.SECONDS.sleep(5L);
		}
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

	private static Pipeline buildPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<MetricVo, Long, MetricVo>mapJournal(IN_MAP_NAME, mapPutEvents(), mapEventNewValue(), START_FROM_CURRENT))
				.addTimestamps(MetricVo::getGenerateTime, 0)
				.window(WindowDefinition.tumbling(5000))
				.groupingKey(MetricVo::getMetricId)
				//.aggregate(caculateMetric(metricVo -> metricVo.getMetricValue()))
				//.aggregate(caculateMetric(MetricVo::getMetricValue))
				//.aggregate(caculateMetric(MetricVo::getMetricValue))
				.aggregate(AggregateOperations.averagingDouble(MetricVo::getMetricValue))
				//.drainTo(Sinks.map(OUT_MAP_NAME));
				//.drainTo(Sinks.list(OUT_MAP_NAME));
				//.drainTo(Sinks.logger((TimestampedEntry<Long, MetricResultVo> t) -> t.toString()));
				//.drainTo(Sinks.files("F:\\Techsure\\ts-metric\\data"));
				.drainTo(Sinks.logger());
		//.drainTo(getCustomSink());
		//.drainTo(Sinks.builder(DistributedFunction.identity()).onReceiveFn((DistributedBiConsumer<JetInstance, Object>) (jetInstance, o) -> System.out.println(o)).build());
		return p;

	}
}
