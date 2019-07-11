package com.techsure.strange.basetype;

import jnr.ffi.annotations.In;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2018/8/24 16:30
 */
public class MapTest {
	private static final Logger logger = LoggerFactory.getLogger(MapTest.class);



	@Test
	public void testComputeIfAbsent(){
		Map<String,Integer> map = new HashMap<>();
		map.putIfAbsent("123", 1);
		for(Map.Entry<String,Integer> entry:map.entrySet()){
			logger.info(entry.getKey());
			logger.info("{}",entry.getValue());
		}
	}

	@Test
	public void testGet() {
		Map<String, List<Integer>> map = new HashMap<>();
		List<Integer> list = map.getOrDefault("12", new ArrayList<>());
		list.add(1);
		map.put("12",list);

		for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
			logger.info(entry.getKey());
			for (Integer integer : entry.getValue()) {
				logger.info("value:{}", integer);
			}
		}
	}

	@Test
	public void testGetOrDefault() {
		Map<String, String> map = new HashMap<>();
		map.put("default", "default");
		map.put("linux", "linux");

		logger.info(map.getOrDefault("li", "default"));
	}

	@Test
	public void testMap() {
		Map<String, String> map = new HashMap<>(10);
		for (int i = 0; i < 1; i++) {
			map.put(String.valueOf(i), String.valueOf(i));
		}
		logger.info(String.valueOf(map.size()));
		logger.info((String) map.values().toArray()[0]);

	}
}
