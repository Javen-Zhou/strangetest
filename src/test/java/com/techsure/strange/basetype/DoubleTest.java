package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhoujian
 * @Date 2018/8/15 11:31
 */
public class DoubleTest {
	private static final Logger logger = LoggerFactory.getLogger(DoubleTest.class);


	@Test
	public void testSimple() {
		System.out.println((float) (5 + 1) / 100);
		System.out.println((double) (0.05 + 0.01));
		System.out.println(1.0 - 0.42);
		System.out.println(4.015 * 100);
		System.out.println(123.3 / 100);
	}

	@Test
	public void testAdd() {
		Double b1 = 0.05;
		Double b2 = 0.0;
		logger.info(String.valueOf(b1 + b2));
		BigDecimal bd1 = new BigDecimal(Double.toString(b1));
		BigDecimal bd2 = new BigDecimal(Double.toString(b2));
		logger.info(String.valueOf(bd1.add(bd2).doubleValue()));
	}

	@Test
	public  void testSort() {
		List<Double> list = new ArrayList<>();
		list.add(2.1);
		list.add(0.3);
		list.add(1.3);
		list.add(0.1);
		list.add(1.0);
		list.add(1.92);
		list.add(1.91);

		list.sort(Double::compareTo);

		list.forEach(System.out::println);

	}

	@Test
	public void testSotr2(){
		List<Double> list = Arrays.asList(50.45,50.45,50.45);
		list.sort(Double::compareTo);
		list.forEach(System.out::println);
	}

	@Test
	public void testNull(){
		List list = Arrays.asList(31.0,31.0,31.0,31.0,31.0,31.0);
		//List<BigDecimal> list = Arrays.asList(new BigDecimal(31.0),new BigDecimal(31.0),new BigDecimal(31.0),new BigDecimal(31.0),new BigDecimal(31.0),new BigDecimal(31.0));
		calculate(list);
	}

	private void calculate(List<BigDecimal> list){
		BigDecimal sum = new BigDecimal(0);
		list.sort(BigDecimal::compareTo);
		Map<Integer, BigDecimal> map = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			map.put(i, list.get(i));
			sum = sum.add(list.get(i));
		}
		logger.info("sum:{}",String.valueOf(sum.doubleValue()));
		logger.info("average:{}",String.valueOf(sum.divide(new BigDecimal(list.size()), 2, BigDecimal.ROUND_HALF_DOWN).doubleValue()));
		logger.info("max:{}",String.valueOf(map.get(list.size() - 1).doubleValue()));
		logger.info("min:{}",String.valueOf(map.get(0).doubleValue()));
		logger.info("num:{}",String.valueOf(list.size()));

		int index95 = list.size() * 95 / 100 - 1;
		//下标从0开始，所以要减1
		if (index95 < 0) {
			index95 = 0;
		}
		int index5 = list.size() * 5 / 100;
		logger.info("p5:{}",String.valueOf(map.get(index5).doubleValue()));

		logger.info("p95:{}",String.valueOf(map.get(index95).doubleValue()));

	}
}
