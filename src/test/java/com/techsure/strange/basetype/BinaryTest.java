package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/11/22 17:45
 */
public class BinaryTest {
	private static final Logger logger = LoggerFactory.getLogger(BinaryTest.class);

	@Test
	public void logBinary(){
		logger.info(Integer.toBinaryString(-2));
		logger.info(Integer.toBinaryString((-2 & 0xFF) + 0x100).substring(1));
	}
}
