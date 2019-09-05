package com.techsure.strange.loader;

/**
 * @author zhoujian
 * @Date 2019/9/4 10:51
 */
public class Father {
	public static String s;

	static {
		s = "father";
		System.out.println(s + "static");
	}

	public Father() {
		System.out.println("Father loader");
	}

	public Father(String test){
		System.out.println("Father test");
	}
}
