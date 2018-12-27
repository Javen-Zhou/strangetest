package com.techsure.strange.simple;

import jnr.ffi.annotations.In;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/12/18 12:22
 */
public class SynchronizedTest {
	private static final Logger logger = LoggerFactory.getLogger(SynchronizedTest.class);


	@Test
	public void Test() throws InterruptedException {
		List<String> nameList = new ArrayList<>();
		nameList.add("TEST1");
		nameList.add("TEST2");


		Thread thread1 = new Thread(() -> {
			getStore("TEST1");
			getStore("TEST2");
		});
		Thread thread2 = new Thread(() -> {
			getStore("TEST1");
			getStore("TEST2");
		});
		Thread thread3 = new Thread(() -> {
			getStore("TEST1");
			getStore("TEST2");
		});
		//Thread thread2 = new Thread(() -> nameList.forEach(this::getStore));
		//Thread thread3 = new Thread(() -> nameList.forEach(this::getStore));

		thread1.start();
		thread2.start();
		thread3.start();

		while (true) {
			TimeUnit.SECONDS.sleep(2);
		}
	}


	private static Map<String, Integer> map = new HashMap<>();

	private void getStore(String name) {
		Integer value = map.get(name);
		if (value == null) {
			synchronized (name) {
				value = map.get(name);
				if(value == null) {
					logger.info(name);
					map.put(name, 1);
				}
			}

		}

	}
}
