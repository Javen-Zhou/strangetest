package com.techsure.strange.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhoujian
 * @Date 2018/7/31 10:08
 */
public class SubListTest {
	public static void main(String[] args){
		List<Integer> list = new ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		List<Integer> list2 = list.subList(0,5);
		list2.forEach(System.out::println);
	}
}
