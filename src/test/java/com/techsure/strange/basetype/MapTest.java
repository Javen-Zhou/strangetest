package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2018/8/24 16:30
 */
public class MapTest {
	private static final Logger logger = LoggerFactory.getLogger(MapTest.class);

	@Test
	public void testMap() {
		Map<String, String> map = new HashMap<>(10);
		for (int i = 0; i < 20; i++) {
			map.put(String.valueOf(i), String.valueOf(i));
		}
		logger.info(String.valueOf(map.size()));
	}
}
