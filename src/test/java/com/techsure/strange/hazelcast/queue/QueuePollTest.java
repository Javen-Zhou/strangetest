package com.techsure.strange.hazelcast.queue;

import com.hazelcast.core.IQueue;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/9/4 星期二 19:48
 */
public class QueuePollTest {
	private static final Logger logger = LoggerFactory.getLogger(QueuePollTest.class);

	@Test
	public void testPoll() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		JetInstance jetB = Jet.newJetInstance();

		IQueue<Integer> queue = jet.getHazelcastInstance().getQueue("queue");
		IQueue<Integer> queueB = jet.getHazelcastInstance().getQueue("queue");

		for(int i=0;i<10000;i++){
			queue.put(i);
		}

		Thread thread = new Thread(()->{
			Integer countA = 0;
			while(true) {
				Integer num = queue.poll();
				//logger.info("AThread - {}", num);
				if (num == null && queue.size() > 0) {
					logger.info("amazing");
				}
				if (num != null) {
					countA++;
				}
				if(num == null && queue.size() ==0){
					logger.info("countA:{}",countA);
					break;
				}
			}
		});

		Thread threadB = new Thread(()->{
			Integer countB = 0;
			while(true) {
				Integer num = queueB.poll();
				//logger.info("BThread - {}", num);
				if (num == null && queue.size() > 0) {
					logger.info("amazing");
				}
				if (num != null) {
					countB++;
				}
				if(num == null && queue.size() ==0){
					logger.info("countB:{}",countB);
					break;
				}
			}
		});

		thread.start();
		threadB.start();
		while (true){
			TimeUnit.SECONDS.sleep(1L);
		}
	}
}
