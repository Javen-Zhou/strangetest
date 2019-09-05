package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/15 15:23
 */
public class ListTest {
	private static final Logger logger = LoggerFactory.getLogger(ListTest.class);

	@Test
	public void testCompare(){
		List<Integer> list = Arrays.asList(70,
				62,
				34,
				37,
				36,
				64,
				61,
				3 ,
				22,
				71,
				60,
				35,
				1 ,
				23,
				27,
				5 ,
				59,
				33,
				28,
				39,
				31,
				48,
				24,
				2 ,
				25,
				63,1,
				2,
				3,
				5,
				22,
				23,
				24,
				25,
				27,
				28,
				31,
				33,
				34,
				35,
				36,
				37,
				39,
				48,
				50,
				51,
				52,
				54,
				56,
				57,
				59,
				60,
				61,
				62,
				63,
				64,
				70);
		//list.sort(Double::compare);
		Collections.sort(list);
		list.forEach(e->logger.info("num:{}",e));

	}

	@Test
	public void testSort(){
		List<Double> list = Arrays.asList( 40.97,
		43.04,
		43.91,
		41.42,
		39.92,
		37.95,
		46.02);
		Collections.sort(list);
		list.forEach(e -> logger.info("num:{}",e));
	}

	@Test
	public void testSubList2(){
		List<Integer> list = new ArrayList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		List<Integer> copyList = list.subList(2, 6);
		for(int i=0;i<copyList.size();i++){
			logger.info(copyList.get(i) + "");
		}
	}

	@Test
	public void testToString(){
		List<Long> list = new ArrayList<>();
		list.add(1L);
		list.add(2L);
		logger.info(list.toString());
	}

	@Test
	public void testThreadList() throws InterruptedException {
		List<TestVo> list = new ArrayList<>();
		list.add(new TestVo().setName("test1").setAge(10).setAddress("固戍"));
		list.add(new TestVo().setName("test2").setAge(11).setAddress("西乡"));
		list.add(new TestVo().setName("test3").setAge(12).setAddress("平洲"));

		post(list);
		list = new ArrayList<>();

		while (true) {
			TimeUnit.SECONDS.sleep(5L);
		}
	}

	private void post(List<TestVo> list){
		Thread thread = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2L);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
			for (TestVo testVo : list) {
				logger.info(testVo.toString());
			}
		});
		thread.start();
	}

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

	@Test
	public void testSubList() {
		List<Integer> list = new ArrayList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		List<Integer> copyList = list.subList(0, 4);
		logger.info("list size is:{}", list.size());
		list.forEach(e -> logger.info(e.toString()));
		logger.info("=========================");
		logger.info("subList size is {}", copyList.size());
		copyList.forEach(e -> logger.info(e.toString()));


		copyList.remove(0);
		logger.info("========================");
		logger.info("list size is {}", list.size());
		list.forEach(e -> logger.info(e.toString()));
		logger.info("========================");
		logger.info("subList size is {}", copyList.size());
		copyList.forEach(e -> logger.info(e.toString()));


	}
}

class TestVo {
	private String name;
	private Integer age;
	private String address;

	public String getName() {
		return name;
	}

	public TestVo setName(String name) {
		this.name = name;
		return this;

	}

	public Integer getAge() {
		return age;
	}

	public TestVo setAge(Integer age) {
		this.age = age;
		return this;

	}

	public String getAddress() {
		return address;
	}

	public TestVo setAddress(String address) {
		this.address = address;
		return this;
	}

	@Override
	public String toString() {
		return "TestVo{" +
				"name='" + name + '\'' +
				", age=" + age +
				", address='" + address + '\'' +
				'}';
	}
}