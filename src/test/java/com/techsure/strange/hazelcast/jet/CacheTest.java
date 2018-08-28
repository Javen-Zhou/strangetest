package com.techsure.strange.hazelcast.jet;

import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/26 17:01
 */
public class CacheTest {
	private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);

	@Test
	public void testCache() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();


		while(true){
			TimeUnit.SECONDS.sleep(10L);
		}
	}

	private JetConfig getJetConfig(){
		JetConfig cfg = new JetConfig();
		cfg.getHazelcastConfig()
				.getCacheEventJournalConfig("inputCache")
				.setEnabled(true)
				.setCapacity(1000)
				.setTimeToLiveSeconds(10);
		return cfg;
	}
}
