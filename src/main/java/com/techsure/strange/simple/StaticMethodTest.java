package com.techsure.strange.simple;

/**
 * @author zhoujian
 * @Date 2018/8/13 16:09
 */
public class StaticMethodTest {

	public static void main(String[] args){
		TestVo testVo = new TestVo();
		testVo.setName("Test");
		System.out.println(testVo.getName());

		TestVo testVo1 = new TestVo();
		System.out.println(testVo1.getName());
	}
}

class TestVo{
	private static String name;

	public  String getName() {
		return name;
	}

	public void setName(String name) {
		TestVo.name = name;
	}
}
