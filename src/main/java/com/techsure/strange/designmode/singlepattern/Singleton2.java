package com.techsure.strange.designmode.singlepattern;

/**
 * @author zhoujian
 * @Date 2018/7/7 10:23
 * 线程不安全
 */
public class Singleton2{
	private static Singleton2 singleton = null;
	//限制产生多个对象
	private Singleton2(){

	}
	//通过该方法获得实例对象
	public static Singleton2 getSingleton(){
		if(singleton == null){
			singleton = new Singleton2();
		}
		return singleton;
	}
}