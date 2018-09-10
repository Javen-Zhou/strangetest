package com.techsure.strange.threadpool;

import com.techsure.strange.util.ThreadPool;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhoujian
 * @Date 2018/9/4 星期二 15:46
 */
public class AutomicTest {
	private static final Logger logger = LoggerFactory.getLogger(AutomicTest.class);

	@Test
	public void testAutomic() throws InterruptedException {
		AtomicReference<Boolean> result = new AtomicReference<>(false);
		ThreadPool.execute(()->{
			result.set(false);
		});
		TimeUnit.SECONDS.sleep(1L);
		logger.info(String.valueOf(result.get()));
	}
}
