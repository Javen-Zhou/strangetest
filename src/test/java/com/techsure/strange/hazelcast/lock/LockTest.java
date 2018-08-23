package com.techsure.strange.hazelcast.lock;

import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author zhoujian
 * @Date 2018/8/9 21:20
 */
public class LockTest {

	private static final Logger logger = LoggerFactory.getLogger(LockTest.class);

	@Test
	public void instanceA() throws InterruptedException {
		JetInstance jetInstance = Jet.newJetInstance();
		Lock lock = jetInstance.getHazelcastInstance().getLock("Test");
		lock.lock();
		while(true){
			logger.info("instanceA");
			TimeUnit.SECONDS.sleep(1L);
		}

	}

	@Test
	public void instanceB() throws InterruptedException {
		JetInstance jetInstance = Jet.newJetInstance();
		Lock lock = jetInstance.getHazelcastInstance().getLock("Test");
		lock.lock();
		while(true){
			logger.info("instanceB");
			TimeUnit.SECONDS.sleep(1L);
		}
	}
}
