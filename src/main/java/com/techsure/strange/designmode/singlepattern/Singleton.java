package com.techsure.strange.designmode.singlepattern;

/**
 * @author zhoujian
 * @Date 2018/7/6 18:10
 */
public class Singleton {
	private static final Singleton singleton = new Singleton();

	private Singleton(){

	}

	public static Singleton getSingleton(){
		return singleton;
	}

	public void doSomething(){
		System.out.println(Thread.currentThread().getName());
	}
}
