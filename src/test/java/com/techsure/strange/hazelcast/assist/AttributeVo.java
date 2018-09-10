package com.techsure.strange.hazelcast.assist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2018/8/26 星期日 14:46
 */
public class AttributeVo extends CommonVo implements Serializable{

	private String attrType;
	private String object;
	private String tags;
	private String attribute;

	public AttributeVo() {

	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setTags(Map<String, String> tags) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		this.tags = mapper.writeValueAsString(tags);
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
