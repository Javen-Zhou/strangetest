package com.techsure.strange.leetcode;

import org.junit.Test;

/**
 * @author zhoujian
 * @Date 2019/3/28 15:30
 */
public class SoupServings {

	@Test
	public void testMain() {
		Long beginTime = System.currentTimeMillis();
		//System.out.println(soupServings(800));
		System.out.println(soupServings2(800));
		System.out.println(System.currentTimeMillis() - beginTime);
	}

	public double soupServings2(int N) {
		if (N >= 4800) {
			return 1.0;
		}
		int n = (int) Math.ceil(N / 25.0);
		double[][] dp = new double[n + 1][n + 1];
		//特殊条件
		dp[0][0] = 0.5;
		for (int i = 1; i < n + 1; i++) {
			dp[i][0] = 0;
			dp[0][i] = 1;
		}
		for (int i = 1; i < n + 1; i++) {
			int a1 = i - 4 > 0 ? i - 4 : 0;
			int a2 = i - 3 > 0 ? i - 3 : 0;
			int a3 = i - 2 > 0 ? i - 2 : 0;
			int a4 = i - 1 > 0 ? i - 1 : 0;
			for (int j = 1; j < n + 1; j++) {
				int b1 = j;
				int b2 = j - 1 > 0 ? j - 1 : 0;
				int b3 = j - 2 > 0 ? j - 2 : 0;
				int b4 = j - 3 > 0 ? j - 3 : 0;
				dp[i][j] = 0.25 * (dp[a1][b1] + dp[a2][b2] + dp[a3][b3] + dp[a4][b4]);
			}
		}
		return dp[n][n];
	}

	public double soupServings(int N) {
		return soupServings((int) Math.ceil(N / 25), (int) Math.ceil(N / 25));
	}

	public double soupServings(int aN, int bN) {
		return 0.25 * (choice(aN, bN, 4, 0) + choice(aN, bN, 3, 1) + choice(aN, bN, 2, 2) + choice(aN, bN, 1, 3));
	}


	public double choice(int aN, int bN, int m, int n) {
		if (aN <= m) {
			if (bN <= n) {
				return 0.5;
			} else {
				return 1.0;
			}
		} else {
			if (bN <= n) {
				return 0;
			} else {
				return soupServings((int) Math.ceil(aN - m), (int)Math.ceil(bN - n));
			}
		}
	}


}
