package com.techsure.strange.basetype;

import jnr.ffi.annotations.In;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoujian
 * @Date 2018/8/24 16:30
 */
public class MapTest {
	private static final Logger logger = LoggerFactory.getLogger(MapTest.class);

	@Test
	public void testCompute(){
		Map<Long,Integer> map = new HashMap<>();
		Integer total = map.get(1L);
		logger.info(total == 0 ? "0":total.toString());
	}

	@Test
	public void testRemove(){
		Map<String,Integer> map = new HashMap<>();
		map.remove("test1");
	}

	@Test
	public void testMapPut() {
		Map<String, List<Integer>> map = new HashMap<>();
		logger.info(map.get("test1") == null?"yes":"no");
		List<Integer> tmpList1 = map.computeIfAbsent("test1", k -> {
			logger.info(k);
			return new ArrayList<>();});
//		tmpList1.add(1);
//		tmpList1.add(2);

		List<Integer> tmpList2 = map.get("test1");
//		tmpList2.forEach(e -> logger.info(String.valueOf(e)));
		logger.info(tmpList2 == null?"yes":"no");
	}


	@Test
	public void testGetOrDefault2() {
		Map<String, List<Integer>> map = new HashMap<>();
		List<Integer> list = map.getOrDefault("key", new ArrayList<>());
		map.putIfAbsent("key", list);
		list.add(1);
		list = map.get("key");
		list.forEach(i -> logger.info("{}", i));
	}

	@Test
	public void testComputeIfAbsent() {
		Map<String, Integer> map = new HashMap<>();
		map.putIfAbsent("123", 1);
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			logger.info(entry.getKey());
			logger.info("{}", entry.getValue());
		}
	}

	@Test
	public void testGet() {
		Map<String, List<Integer>> map = new HashMap<>();
		List<Integer> list = map.getOrDefault("12", new ArrayList<>());
		list.add(1);
		map.put("12", list);

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
