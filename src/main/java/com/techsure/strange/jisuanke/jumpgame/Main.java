package com.techsure.strange.jisuanke.jumpgame;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author zhoujian
 * @Date 2018/10/19 18:13
 */
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Integer num = sc.nextInt();
		Integer[] arr = new Integer[num];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = sc.nextInt();
		}

		System.out.println(find(arr));
	}


	private static Boolean find(Integer[] arr) {

		for (int i = arr.length - 1; i >= 0; i--) {
			if (i > 0) {
				if (arr[i - 1] >= arr.length - i) {
					Integer[] newArr = Arrays.copyOfRange(arr, 0, i - 1);
					if (find(newArr)) {
						return true;
					}
				}
			} else {
				if(arr[i] >= arr.length - 1){
					return true;
				}
			}
		}
		return false;
	}
}
