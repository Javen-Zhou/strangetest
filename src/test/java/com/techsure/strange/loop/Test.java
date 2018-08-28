package com.techsure.strange.loop;

import com.hazelcast.core.Hazelcast;

/**
 * @author zhoujian
 * @Date 2018/8/24 12:15
 */
public class Test {

	@org.junit.Test
	public void test() throws InterruptedException {
		LoopPlaceState.init(Hazelcast.newHazelcastInstance());
	}
}
