package com.techsure.strange.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhoujian
 * @Date 2019/7/19 10:06
 */
public class ProxyFactory {
	private static final Logger logger = LoggerFactory.getLogger(ProxyFactory.class);
	private Object target;

	public ProxyFactory(Object target) {
		this.target = target;
	}

	public Object getProxyInstance() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				(proxy, method, args) -> {
					logger.info("开启事务");
					Object returnValue = method.invoke(target, args);
					logger.info("关闭事务");
					return returnValue;
				});
	}
}
