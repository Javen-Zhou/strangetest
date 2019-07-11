package com.techsure.strange.leetcode;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/6/19 18:05
 */
public class StringToInteger {
	private static final Logger logger = LoggerFactory.getLogger(StringToInteger.class);

	@Test
	public void test() {
		logger.info("{}", myAtoi("-2147483649"));
	}

	public int myAtoi(String str) {
		int length = str.length();
		int result = 0;
		char c;
		boolean isInit = false, isNegative = false;
		for (int i = 0; i < length; i++) {
			c = str.charAt(i);
			if (!isInit) {
				if (c == 45) {
					isNegative = true;
					isInit = true;
					continue;
				}
				if(c == 43){
					isInit = true;
					continue;
				}
				if (c >= 48 && c <= 57) {
					isInit = true;
					result = Integer.valueOf(String.valueOf(c));
					continue;
				}
				if(c != 32){
					return 0;
				}
				continue;
			}
			if (c < 48 || c > 57) {
				return result;
			}
			if (!isNegative) {
				if (result > Integer.MAX_VALUE / 10 || result * 10 > Integer.MAX_VALUE - Integer.valueOf(String.valueOf(c))) {
					return Integer.MAX_VALUE;
				}
				result = result * 10 + Integer.valueOf(String.valueOf(c));
			} else {
				if (result < Integer.MIN_VALUE / 10 || Integer.valueOf(String.valueOf(c)) + Integer.MIN_VALUE > result * 10) {
					return Integer.MIN_VALUE;
				}
				result = result * 10 - Integer.valueOf(String.valueOf(c));
			}
		}
		return result;
	}

	@Test
	public void testMx() {
		char c = '9';
		logger.info(Integer.valueOf(String.valueOf(c)) + "");
		int t = Integer.MAX_VALUE / 10;
		logger.info(t + "");
		logger.info(String.valueOf(214748365 > Integer.MAX_VALUE / 10));
	}
}
