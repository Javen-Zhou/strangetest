package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhoujian
 * @Date 2018/8/31 星期五 14:22
 */
public class QueueTest {
	private static final Logger logger = LoggerFactory.getLogger(QueueTest.class);

	@Test
	public void testPut(){
		BlockingQueue queue = null;
		try {
			queue.put("");
		} catch (InterruptedException e) {
			logger.error(e.getMessage(),e);
		}
	}
}
