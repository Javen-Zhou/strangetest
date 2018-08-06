package com.techsure.strange.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhoujian
 * @Date 2018/7/30 10:08
 */
public class ListAddTest {
	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2));
		List<Integer> list2 = new ArrayList<>(Arrays.asList(2, 3));
		list1.addAll(list2);
		list1.forEach(System.out::println);
	}
}
