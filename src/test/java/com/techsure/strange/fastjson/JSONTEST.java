package com.techsure.strange.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;

/**
 * @author zhoujian
 * @Date 2019/1/8 16:09
 */
public class JSONTEST {

	@Test
	public void writeNullStringAsEmpty() {
		TestVO testVO = new TestVO();
		testVO.setName("hahaha");

		System.out.println(JSONObject.toJSONString(testVO));
		System.out.println("设置WriteMapNullValue后：");
		System.out.println(JSONObject.toJSONString(testVO, SerializerFeature.WriteMapNullValue));
		System.out.println("设置WriteMapNullValue、WriteNullStringAsEmpty后：");
		System.out.println(JSONObject.toJSONString(testVO, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
	}


}

class TestVO{
	private String id;
	private String name;
	private String data;
	private Integer code;
	private String msg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
