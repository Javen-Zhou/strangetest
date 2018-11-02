package com.techsure.strange.basetype;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author zhoujian
 * @Date 2018/8/15 15:23
 */
public class ListTest {
	private static final Logger logger = LoggerFactory.getLogger(ListTest.class);

	@Test
	public void testOut() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		logger.error("list:{}", list);
	}

	@Test
	public void testSameList() {
		List list1 = Arrays.asList(1, 2, 3);
		List list2 = new ArrayList(list1);
		logger.info("test");
	}


	@Test
	public void testListSort() {
		List<Integer> list = Arrays.asList(3, 2, 1, 5, 2, 4);
		list.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {

				if (o1 == o2) {
					return 0;
				}
				return o1 < o2 ? -1 : 1;
			}
		});
		list.forEach(System.out::println);
	}
}
