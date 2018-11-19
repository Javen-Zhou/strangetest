package com.techsure.strange.basetype;

import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/8/24 16:43
 */
public class HashCodeTest {
	private static final Logger logger = LoggerFactory.getLogger(HashCodeTest.class);


	@Test
	public void testHashCode() {
		String metricId = "999998";
		int hash = metricId.hashCode();
		logger.info(String.valueOf(hash));
		if (hash < 0) {
			hash = -hash;
		}
		int place = hash % 1024;
		logger.info(String.valueOf(place));
	}

	@Test
	public void testHash() {
		String s1 = "test1";
		String s2 = "test2";

		logger.info(String.valueOf(s1.hashCode()));
		logger.info(String.valueOf(s2.hashCode()));

	}

}
