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
	public void testNullSet(){
		TestVo testVo = new TestVo();
		testVo.setAddress(null);
	}

	@Test
	public void testHash(){
		String test = "QOZRigKHq/y0MUElYC1M/w==";
		logger.info(test.hashCode() + "");
	}

	@Test
	public void testError(){
		try {
			for (int i = 0; i < 10; i++) {
				if (i == 5) {
					throw new RuntimeException("ERROR TEST");

				} else {
					logger.info(i + "");
				}
			}
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Test
	public void testBool(){
		if(!true && true){
			logger.info("true");
		}
	}
}

