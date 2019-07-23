package com.techsure.strange.basetype;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author zhoujian
 * @Date 2018/8/31 星期五 10:56
 */
public class JSONTest {
	private static final Logger logger = LoggerFactory.getLogger(JSONTest.class);

	@Test
	public void testJSONArray() {
		String jsonStr = "{  \n" +
				"   \"def\":[  \n" +
				"      {  \n" +
				"         \"name\":\"custom_sql_1\",\n" +
				"         \"sql\":\"abc\",\n" +
				"         \"level\":\"1:Warning\",\n" +
				"   \"cron\":\"1-2 * * * * ? *\",\n" +
				"   \"actionIds\":[1,2],\n" +
				"   \"recipients\":\"majr@techsure.com.cn\"\n" +
				"      },\n" +
				"       {  \n" +
				"         \"name\":\"custom_sql_1\",\n" +
				"         \"sql\":\"abc\",\n" +
				"         \"level\":\"1:Warning\",\n" +
				"   \"cron\":\"1-2 * * * * ? *\",\n" +
				"   \"actionIds\":[1,2],\n" +
				"   \"recipients\":\"majr@techsure.com.cn\"\n" +
				"      }\n" +
				"   ]\n" +
				"}";
		JSONObject obj = JSON.parseObject(jsonStr);
		JSONArray actionIds = obj.getJSONArray("def").getJSONObject(0).getJSONArray("actionIds");
		for (int i = 0; i < actionIds.size(); i++) {
			logger.info("actionId:{}", actionIds.get(i));
		}
	}


	@Test
	public void toObjcet() {
		String json = "{\"name\":\"testVo1Name\",\"values\":[[10,0],[20,1]]\"list\":[{\"value\":20,\"age\":18},{\"value\":22,\"age\":19}]}";
		JSONObject data = JSONObject.parseObject(json);
		TestVo1 testVo1 = JSON.toJavaObject(data, TestVo1.class);
		logger.info("success");
	}

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

class TestVo1 {
	private String name;
	private List<TestVo2> list;
	private List<JSONArray> values;

	public List<JSONArray> getValues() {
		return values;
	}

	public void setValues(List<JSONArray> values) {
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TestVo2> getList() {
		return list;
	}

	public void setList(List<TestVo2> list) {
		this.list = list;
	}
}

class TestVo2 {
	private String value;
	private Integer age;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}