package com.techsure.strange.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/7/19 10:15
 */
public class Dog {
	private static final Logger logger = LoggerFactory.getLogger(Dog.class);

	public void run(){
		logger.info("dog happy run");
	}
}
