package com.techsure.strange.hazelcast.queue;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.concurrent.BlockingQueue;

/**
 * @author zhoujian
 * @Date 2018/8/6 18:20
 */
public class DistrbutedQueueTest {
	public static void main(String[] args) throws InterruptedException {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();
		BlockingQueue<String>  queue = instance.getQueue("TestQueue");

		queue.offer("Test1");
		queue.offer("Test2");
		queue.offer("Test3");
		queue.offer("Test4");
		queue.offer("Test5");
		queue.offer("Test6");
		queue.offer("Test7");
		queue.offer("Test8");
		queue.offer("Test9");
		queue.offer("Test10");
		queue.offer("Test11");
		queue.offer("Test12");
		queue.offer("Test13");
		queue.offer("Test14");
		queue.offer("Test15");
		queue.offer("Test16");
		queue.offer("Test17");
		queue.offer("Test18");
		queue.offer("Test19");
		queue.offer("Test20");
		queue.offer("Test21");
		queue.offer("Test22");
		queue.offer("Test23");
		queue.offer("Test24");
		queue.offer("Test25");
		queue.offer("Test26");
		queue.offer("Test27");
	}


}
