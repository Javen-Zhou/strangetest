package com.techsure.strange.hash;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/9/11 星期二 18:04
 */
public class Hash {
	private static final Logger logger = LoggerFactory.getLogger(Hash.class);

	@Test
	public void testHash(){
		String metricId = "14922";
		logger.info(String.valueOf(metricId.hashCode()%1024));
	}
}
