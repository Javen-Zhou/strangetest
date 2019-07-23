package com.techsure.strange.proxy;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/7/19 10:10
 */
public class TestProxyFactory {
	private static final Logger logger = LoggerFactory.getLogger(TestProxyFactory.class);

	@Test
	public void testPerson(){
		IPerson person = new Male();
		logger.info(person.getClass().toString());

		IPerson proxy = (IPerson) new ProxyFactory(person).getProxyInstance();
		logger.info(proxy.getClass().toString());
		logger.info(proxy.say());
		proxy.run();
		proxy.eat();
	}
}
