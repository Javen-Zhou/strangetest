package com.techsure.strange.loader;

/**
 * @author zhoujian
 * @Date 2019/9/4 11:24
 */
public class Daughter extends Father {

	static {
		System.out.println(s);
	}

	public Daughter() {
		System.out.println("Daughter loader");
	}

	public Daughter(String test) {
		System.out.println("Daughter test");
	}
}
