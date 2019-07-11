package com.techsure.strange.hash;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/6/19 10:54
 */
public class PersonTest {
	private static final Logger logger = LoggerFactory.getLogger(PersonTest.class);

	@Test
	public void testEquals() {
		Person A = new Person("A", 18);
		Person B = new Person("A", 18);
		logger.info("A.equals(B):{}", A.equals(B));
		logger.info("A==B:{}", A == B);
	}
}
