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
	private static HazelcastInstance instance;
	public static void main(String[] args) throws InterruptedException {
		HazelcastInstance instance = getInstance();
		String path = Class.class.getClass().getResource("/").getPath();
		String path2 = System.getProperty("user.dir");
		BlockingQueue<String> queue = instance.getQueue("TestQueue");

		String str = "";

		while(true){
			str = queue.take();
			if(StringUtils.isNotBlank(str)){
				System.out.println("Instance A:" + str);
			}
		}
	}

	private static HazelcastInstance getInstance(){
		if(instance == null){
			instance = Hazelcast.newHazelcastInstance();
		}
		return instance;
	}
}
