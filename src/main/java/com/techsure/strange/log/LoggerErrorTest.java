package com.techsure.strange.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/7/28 16:31
 */
public class LoggerErrorTest {
	private static final Logger logger =LoggerFactory.getLogger(LoggerErrorTest.class);

	public static void main(String[] args){
		try{
			TestVo testVo = new TestVo();
			if(testVo.getName().equalsIgnoreCase("ah")){
				System.out.println("test");
			}
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}
}

class TestVo{
	private String name;
	private Integer age;

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
}
