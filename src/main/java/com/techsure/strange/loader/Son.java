package com.techsure.strange.loader;

/**
 * @author zhoujian
 * @Date 2019/9/4 10:52
 */
public class Son extends Father {

	static{
		s = "Son";
		System.out.println(s);
	}

	public Son() {
		System.out.println("Son loader");
	}

	public Son(String test){
		System.out.println("Son Test");
	}
}
