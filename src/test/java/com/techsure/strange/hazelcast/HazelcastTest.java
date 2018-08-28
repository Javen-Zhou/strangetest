package com.techsure.strange.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/24 11:44
 */
public class HazelcastTest {
	private static final Logger logger = LoggerFactory.getLogger(HazelcastTest.class);

	@Test
	public void testPort() throws InterruptedException {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();
		logger.info(String.valueOf(instance.getCluster().getLocalMember().getAddress().getPort()));
		TimeUnit.SECONDS.sleep(10L);
		instance.shutdown();
	}
}
