package com.techsure.strange.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author zhoujian
 * @Date 2018/7/19 18:01
 */
public class ComparatorTest {
	public static void main(String[] args){
		List<Integer> list = Arrays.asList(1,2,5,4,3);
		list.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.equals(o2) ? 0 : (o1 < o2 ? -1 : 1);
			}
		});

		list.forEach(System.out::println);
	}
}
