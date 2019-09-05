package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sun.xml.internal.fastinfoset.util.ValueArray.MAXIMUM_CAPACITY;

/**
 * @author zhoujian
 * @Date 2018/11/22 17:45
 */
public class BinaryTest {
	private static final Logger logger = LoggerFactory.getLogger(BinaryTest.class);

	@Test
	public void testRightMove() {
		int n = 10 - 1;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		int value = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
		logger.info("{}",value);
	}

	@Test
	public void logBinary() {
		logger.info(Integer.toBinaryString(-2));
		logger.info(Integer.toBinaryString((-2 & 0xFF) + 0x100).substring(1));
	}
}
