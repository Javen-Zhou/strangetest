package com.techsure.strange.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2019/3/28 14:02
 */
public class KeepCitySkyLine {

	@Test
	public void testMax() {

		int[][] grid = {{59, 88, 44}, {3, 18, 38}, {21, 26, 51}};
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j] + "   ");
			}
			System.out.print("\n");
		}
		System.out.println(maxIncreaseKeepingSkyline(grid));
	}

	public int maxIncreaseKeepingSkyline(int[][] grid) {
		int totalCount = 0;
		int slideLen = grid.length;
		int[] colMax = new int[slideLen];
		int[] rowMax = new int[slideLen];

		for (int i = 0; i < slideLen; i++) {
			rowMax[i] = getMax(grid[i]);
			for (int j = 0; j < slideLen; j++) {
				if (colMax[j] == 0) {
					for (int k = 0; k < slideLen; k++) {
						colMax[j] = getMax(colMax[j], grid[k][j]);
					}
				}
				if(grid[i][j] == rowMax[i] || grid[i][j] == colMax[j]){
					continue;
				}
				totalCount += Math.min(colMax[j],rowMax[i]) - grid[i][j];
			}
		}


		return totalCount;
	}


	public int getMax(int... num) {
		int max = 0;
		for (int i = 0; i < num.length; i++) {
			max = Math.max(max, num[i]);
		}
		return max;
	}
}
