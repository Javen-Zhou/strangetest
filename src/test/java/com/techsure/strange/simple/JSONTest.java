package com.techsure.strange.simple;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/3/4 14:02
 */
public class JSONTest {
	private static final Logger logger = LoggerFactory.getLogger(JSONTest.class);

	@Test
	public void testToObject(){
		String json = "{\"name\":\"zhangsan\",\"age\":18,\"phoneNumber\":\"12345678\"}";
		TestVo testVo = JSONObject.toJavaObject(JSONObject.parseObject(json),TestVo.class);
		logger.info(testVo.toString());
		logger.info(testVo.getName() + "");
	}


}

class TestVo{
	private String name;
	private Integer age;
	private String phoneNumber;
	private boolean trigger;

	public boolean getTrigger() {
		return trigger;
	}

	public void setTrigger(Boolean trigger) {
		this.trigger = trigger;
	}

	@Override
	public String toString() {
		return "TestVo{" +
				"name='" + name + '\'' +
				", age=" + age +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
