package com.techsure.strange.hazelcast.jet;

import com.hazelcast.jet.IMapJet;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.pipeline.JournalInitialPosition;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/7/12 15:12
 */
public class TestJetConfig {
	private static final String MAP_NAME = "map";
	private static final String LIST_NAME = "list";

	public static void main(String[] args){
		JetConfig cfg = getJetConfig();
		JetInstance jet = Jet.newJetInstance(cfg);
		try {
			jet.newJob(buildPipeline());

			IMapJet<Integer, Integer> map = jet.getMap(MAP_NAME);
			for (int i = 0; i < 100; i++) {
				map.set( i, i);
			}
			TimeUnit.MILLISECONDS.sleep(2000);
			System.out.println("list.size()=" + jet.getList(LIST_NAME).size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jet.shutdown();
		}
	}

	private static Pipeline buildPipeline() {
		Pipeline p = Pipeline.create();
		p.drawFrom(Sources.<Integer,Integer>mapJournal(MAP_NAME, JournalInitialPosition.START_FROM_OLDEST))
				.map(Map.Entry::getValue)
				.drainTo(Sinks.list(LIST_NAME));
		return p;
	}

	private static JetConfig getJetConfig() {
		JetConfig cfg = new JetConfig();
		cfg.getHazelcastConfig()
				.getMapEventJournalConfig(MAP_NAME)
				.setEnabled(true)
				.setCapacity(200)
				.setTimeToLiveSeconds(1);
		return cfg;
	}


}
