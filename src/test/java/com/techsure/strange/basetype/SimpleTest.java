package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/9/2 星期日 13:49
 */
public class SimpleTest {
	private static final Logger logger = LoggerFactory.getLogger(SimpleTest.class);

	@Test
	public void testBool(){
		if(!true && true){
			logger.info("true");
		}
	}
}
