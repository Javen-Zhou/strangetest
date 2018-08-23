package com.techsure.strange.hazelcast.list;

import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/20 17:54
 */
public class ListTest {

	private static final Logger logger = LoggerFactory.getLogger(ListTest.class);

	@Test
	public void testDisList() throws InterruptedException {
		JetInstance jet = Jet.newJetInstance();
		//List<Integer> list = jet.getHazelcastInstance().getList("TESTLIST");
		BlockingQueue queue = jet.getHazelcastInstance().getQueue("TESTQUEUE");
		startThread(5, queue);

		Set set = new HashSet();
		while (true) {
			List list1 = (ArrayList) queue.take();
			set.addAll(list1);
			logger.info(String.valueOf(set.size()));
		}
	}

	private List list = new ArrayList();

	private void startThread(Integer num, BlockingQueue queue) {
		for (int j = 0; j < num; j++) {
			int finalJ = j;
			Thread thread = new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(num - finalJ);
					for (int i = finalJ * 1000; i < (finalJ + 1) * 1000; i++) {
						synchronized (list) {
							if (list.size() > 100) {
								queue.put(list);
								list = new ArrayList();
							}
							list.add(i);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			thread.start();
		}
	}

}
