package com.techsure.strange.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/7/19 10:05
 */
public class Female implements IPerson {
	private static final Logger logger  = LoggerFactory.getLogger(Female.class);
	@Override
	public void run() {
		logger.info("a female run in the park!");
	}

	@Override
	public void eat() {
		logger.info("a female eat one bowl of rice");
	}

	@Override
	public String say() {
		return "hello world,i'm a female";
	}
}
