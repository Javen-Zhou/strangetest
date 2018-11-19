package com.techsure.strange.basetype;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author zhoujian
 * @Date 2018/8/31 星期五 10:56
 */
public class JSONTest {

	public void testJsonForeach() {
		JSONObject json = new JSONObject();
		json.forEach((key, value) -> {

		});
	}

	public void testMap() {
		Map<String, String> map = new HashMap<>();
		map.forEach((k, v) -> {

		});
	}
}
