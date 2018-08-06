package com.techsure.strange.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author zhoujian
 * @Date 2018/8/6 14:59
 */
public class RateLimiterDemo {
	public static void main(String[] args) {
		testNoRateLimiter();
		testWIthRateLimiter();
	}

	public static void testNoRateLimiter() {
		Long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			System.out.println("call execute.." + i);
		}
		Long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	public static void testWIthRateLimiter() {
		Long start = System.currentTimeMillis();
		RateLimiter limiter = RateLimiter.create(10.0);
		for (int i = 0; i < 10; i++) {
			limiter.acquire();
			System.out.println("call execute.." + i);
		}
		Long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
