package com.techsure.strange.hazelcast.jet;

import com.hazelcast.jet.IMapJet;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.aggregate.AggregateOperations;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;
import com.hazelcast.jet.pipeline.WindowDefinition;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import static com.hazelcast.jet.Util.mapEventNewValue;
import static com.hazelcast.jet.Util.mapPutEvents;
import static com.hazelcast.jet.pipeline.JournalInitialPosition.START_FROM_CURRENT;

/**
 * @author zhoujian
 * @Date 2018/8/16 18:12
 */
public class TestJetTimeWindowRound {
	private static final Logger logger = LoggerFactory.getLogger(TestJetTimeWindowRound.class);
	private static final String IN_MAP_NAME = "inMap";

	@Test
	public void test() throws InterruptedException {
		JetConfig jetConfig = getJetConfig();
		JetInstance jet = Jet.newJetInstance(jetConfig);
		JobConfig jobConfig = new JobConfig();
		/*jobConfig.setProcessingGuarantee(ProcessingGuarantee.EXACTLY_ONCE);
		jobConfig.setSnapshotIntervalMillis(SECONDS.toMillis(2));*/
		jobConfig.setName("Test");
		jet.newJob(buildPipeline(), jobConfig);

		TimeUnit.SECONDS.sleep(10L);

		IMapJet<Integer, TestWindowVo> map = jet.getMap(IN_MAP_NAME);

		Long times = 1534390200000L;
		//mes = times + TimeUnit.HOURS.toMillis(8);
		for (int i = 0; i < 100; i++) {
			TestWindowVo testWindowVo = new TestWindowVo();
			testWindowVo.setId(111);
			testWindowVo.setValue(i);
			testWindowVo.setTime(times + TimeUnit.MINUTES.toMillis(1) * i);
			map.set(testWindowVo.getId(), testWindowVo);
		}

		while (true) {
			TimeUnit.SECONDS.sleep(2L);
		}
	}

	private static Pipeline buildPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<TestWindowVo, Long, TestWindowVo>mapJournal(IN_MAP_NAME, mapPutEvents(), mapEventNewValue(), START_FROM_CURRENT))
				.addTimestamps(TestWindowVo::getTime, 0)
				.window(WindowDefinition.tumbling(TimeUnit.MINUTES.toMillis(5)))
				.groupingKey(TestWindowVo::getId)
				.aggregate(AggregateOperations.averagingDouble(TestWindowVo::getValue))
				.drainTo(Sinks.logger());
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
}

class TestWindowVo implements Serializable {
	private Integer value;
	private Integer id;
	private Long time;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
}