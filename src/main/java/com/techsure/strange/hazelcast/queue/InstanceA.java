package com.techsure.strange.hazelcast.queue;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.BlockingQueue;

/**
 * @author zhoujian
 * @Date 2018/8/6 18:39
 */
public class InstanceA {
	public static void main(String[] args) throws InterruptedException {
		HazelcastInstance instance = Hazelcast.newHazelcastInstance();
		BlockingQueue<String> queue = instance.getQueue("TestQueue");

		String str = "";

		while(true){
			str = queue.take();
			if(StringUtils.isNotBlank(str)){
				System.out.println("Instance A:" + str);
			}
		}
	}
}
