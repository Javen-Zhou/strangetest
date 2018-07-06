package com.techsure.strange.designmode.singlepattern;

import com.techsure.strange.util.ThreadPool;

/**
 * @author zhoujian
 * @Date 2018/7/6 19:11
 */
public class TestSingleton {
	public static void main(String[] args) {
		ThreadPool.execute(() -> {
			Singleton singleton = Singleton.getSingleton();
			singleton.doSomething();
		});
	}
}
