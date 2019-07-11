package com.techsure.strange.leetcode;

import jnr.ffi.annotations.In;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/6/19 16:24
 */
public class ReverseInteger {
	private static final Logger logger = LoggerFactory.getLogger(ReverseInteger.class);

	@Test
	public void test() {
		logger.info("{}", reverse(123));
	}

	/**
	 * 1234
	 * length = 4;
	 * first index = 3;
	 * first number = 4;
	 * 4 * 1000 = 4000 = 4 * 10 * 10 * 10
	 *
	 * @param x
	 * @return
	 */
	public int reverse1(int x) {
		boolean isNegative = false;
		if (x < 0) {
			isNegative = true;
			x = -x;
		}
		String str = String.valueOf(x);
		int length = str.length();
		StringBuilder result = new StringBuilder();
		for (int i = length - 1; i >= 0; i--) {
			result.append(str.charAt(i));
		}
		if (isNegative) {
			result.insert(0, "-");
		}
		int finalResult = 0;
		try {
			finalResult = Integer.valueOf(result.toString());
		} catch (Exception e) {
			return 0;
		}
		return finalResult;
	}

	public int reverse(int x) {
		boolean isNegative = x < 0;
		int tmp, result = 0;
		while (x != 0) {
			tmp = x % 10;
			if(!isNegative && (result > Integer.MAX_VALUE / 10 || result * 10 > Integer.MAX_VALUE - tmp)){
				return 0;
			}else if(isNegative && (result < Integer.MIN_VALUE / 10 || result * 10 < Integer.MIN_VALUE - tmp)){
				return 0;
			}
			result = result * 10 + tmp;
			x = x / 10;
		}
		System.out.println();
		return result;
	}
}
