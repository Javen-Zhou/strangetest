package com.techsure.strange.hazelcast.jet;

import com.hazelcast.config.Config;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.config.JetConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/17 19:18
 */
public class JetQueueTest {
	private static final Logger logger = LoggerFactory.getLogger(JetQueueTest.class);

	@Test
	public void testCompareLocalAndDisturbed() throws InterruptedException {
		JetInstance jetA = Jet.newJetInstance();
		//JetInstance jetB = Jet.newJetInstance();

		Thread thread1 = new Thread(() -> {
			BlockingQueue<Integer> queue = jetA.getHazelcastInstance().getQueue("TestQueue");
			Long begin = System.currentTimeMillis();
			for (int i = 0; i < 100000; i++) {
				try {
					queue.put(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			logger.info("A put consume:{}", System.currentTimeMillis() - begin);

			begin = System.currentTimeMillis();
			while (true) {
				try {
					if (queue.size() == 0) {
						logger.info("A take consume:{}", System.currentTimeMillis() - begin);
						break;
					}
					Integer num = queue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread1.setName("D-Q-A");
		thread1.start();

		/*Thread thread2 = new Thread(() -> {
			BlockingQueue<Integer> queue = jetB.getHazelcastInstance().getQueue("TestQueue");
			Long begin = System.currentTimeMillis();
			for (int i = 100000; i < 200000; i++) {
				try {
					queue.put(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			logger.info("B put consume:{}", System.currentTimeMillis() - begin);

			begin = System.currentTimeMillis();
			while (true) {
				try {
					if (queue.size() == 0) {
						logger.info("B take consume:{}", System.currentTimeMillis() - begin);
						break;
					}
					Integer num = queue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});*/
		//thread2.setName("D-Q-B");
		//thread2.start();

		Thread thread3 = new Thread(()->{
			BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();
			Long begin = System.currentTimeMillis();
			for(int i=0;i<1000000;i++){
				try {
					queue.put(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			logger.info("local put consume:{}",System.currentTimeMillis()-begin);

			begin = System.currentTimeMillis();
			while(true){
				try {
					Integer num = queue.take();
					if(queue.size() == 0){
						logger.info("local take consume:{}",System.currentTimeMillis()-begin);
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		thread3.setName("L-Q");
		thread3.start();
		while (true) {
			TimeUnit.SECONDS.sleep(10L);
		}
	}

	@Test
	public void testLocalQueue() throws InterruptedException {
		BlockingQueue queue = new LinkedBlockingDeque();
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			queue.put(i);
		}
		logger.info("local put consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		while (queue.size() != 0) {
			queue.take();
		}
		logger.info("local take consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			queue.offer(i);
		}
		logger.info("local offer consume:{}", System.currentTimeMillis() - begin);

		List list = new ArrayList();
		begin = System.currentTimeMillis();
		while (queue.size() != 0) {
			queue.drainTo(list);
		}
		logger.info("local drainTo consume:{}", System.currentTimeMillis() - begin);
	}

	@Test
	public void testQueueDrainTo() throws InterruptedException {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();

		BlockingQueue queue = instance.getQueue("Test");
		for (int i = 0; i < 20; i++) {
			queue.put(i);
		}

		List list = new ArrayList();
		while (true) {
			queue.drainTo(list, 5);
			logger.info("list.size:{}", list.size());
			list.forEach(e -> logger.info(String.valueOf(e)));
			list = new ArrayList();
			if (queue.size() == 0) {
				break;
			}
		}
	}

	@Test
	public void testDisturbedQueue() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		BlockingQueue queue = jet.getHazelcastInstance().getQueue("TestQUeue");
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			queue.put(i);
		}
		logger.info("Disturbed put consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		while (queue.size() != 0) {
			queue.take();
		}
		logger.info("Disturbed take consume:{}", System.currentTimeMillis() - begin);

		begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			queue.offer(i);
		}
		logger.info("Disturbed offer consume:{}", System.currentTimeMillis() - begin);

		List list = new ArrayList();
		begin = System.currentTimeMillis();
		while (queue.size() != 0) {
			queue.drainTo(list);
		}
		logger.info("Disturbed drainTo consume:{}", System.currentTimeMillis() - begin);

		while (true) {
			TimeUnit.SECONDS.sleep(3L);
		}

	}

	@Test
	public void testA() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		//BlockingQueue queue = jet.getHazelcastInstance().getQueue("TESTQUEUE");
		BlockingQueue queue = new LinkedBlockingDeque();
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			queue.put(i);
		}
		Long end = System.currentTimeMillis();
		logger.info("queue put consume:{}", end - begin);

		begin = System.currentTimeMillis();
		while (queue.size() != 0) {
			Object o = queue.take();
		}
		end = System.currentTimeMillis();
		logger.info("queue take consume:{}", end - begin);

		List list = new ArrayList();
		begin = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			list.add(i);
		}
		queue.put(list);
		end = System.currentTimeMillis();
		logger.info("list add consume:{}", end - begin);

		begin = System.currentTimeMillis();
		List list2 = (ArrayList) queue.take();
		list2.forEach(o -> {
			logger.info(String.valueOf(o));
		});
		end = System.currentTimeMillis();
		logger.info("list take consume:{}", end - begin);
		while (true) {
			TimeUnit.SECONDS.sleep(10L);
		}
	}

	@Test
	public void testB() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		BlockingQueue queue = jet.getHazelcastInstance().getQueue("TESTQUEUE");

		while (true) {
			if (queue.size() > 0) {
				List list = new ArrayList<>();
				queue.drainTo(list, 2);
				list.forEach(e -> logger.info(String.valueOf(e)));
			}
		}

	}

	@Test
	public void testC() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		BlockingQueue queue = jet.getHazelcastInstance().getQueue("TESTQUEUE");

		while (true) {
			if (queue.size() > 0) {
				List list = new ArrayList<>();
				queue.drainTo(list, 2);
				list.forEach(e -> logger.info(String.valueOf(e)));
			}
		}
	}

	@Test
	public void testQ() throws InterruptedException {
		BlockingQueue queue = new LinkedBlockingDeque(10);
		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					queue.put(i);
					System.out.println(System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					System.out.println(queue.take());
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread1.start();
		thread2.start();

		while (true) {
			TimeUnit.SECONDS.sleep(1L);
		}
	}

	@Test
	public void testQueue() throws InterruptedException {
		Config config = new Config();
		QueueConfig queueConfig = config.getQueueConfig("Test1");
		queueConfig.setMaxSize(10);
		config.addQueueConfig(queueConfig);
		JetConfig jetConfig = new JetConfig();
		jetConfig.setHazelcastConfig(config);

		JetInstance jet = Jet.newJetInstance(jetConfig);

		BlockingQueue queue = jet.getHazelcastInstance().getQueue("Test");

		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					queue.put(i);
					System.out.println(System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					System.out.println(queue.take());
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread1.start();
		thread2.start();

		while (true) {
			TimeUnit.SECONDS.sleep(1L);
		}
	}
}
