package com.techsure.strange.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/8/23 19:55
 */
public class ConstructorTest {
	private static final Logger logger = LoggerFactory.getLogger(ConstructorTest.class);

	@org.junit.Test
	public void test() {
		logger.info(Test.STR);
	}
}

class Test {
	public static String STR;

	public Test() {
		System.out.println("init Test");
		STR = "hello world";
	}
}
