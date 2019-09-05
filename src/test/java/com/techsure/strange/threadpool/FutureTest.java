package com.techsure.strange.threadpool;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author zhoujian
 * @Date 2019/8/14 14:18
 */

public class FutureTest {
	private static final Logger logger = LoggerFactory.getLogger(FutureTest.class);

	@Test
	public void testNettyFuture() throws InterruptedException {
		EventExecutorGroup group = new DefaultEventExecutorGroup(100); // 4 threads
		long time = System.currentTimeMillis();

		try {
			CountDownLatch countDownLatch = new CountDownLatch(100);
			for (int i = 0; i < 100; i++) {
				int finalI = i;
				Future<Integer> f = group.submit(() -> {
					Thread.sleep(3000);

					return finalI;
				});

				f.addListener((FutureListener<Object>) objectFuture -> {
					System.out.println("计算结果:" + objectFuture.get());
					countDownLatch.countDown();
				});
				throw new RuntimeException();
			}

			System.out.println("end:" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			countDownLatch.await();//不让守护线程退出
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("cost:{}", System.currentTimeMillis() - time);
	}
}
