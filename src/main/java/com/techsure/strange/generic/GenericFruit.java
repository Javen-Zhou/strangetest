package com.techsure.strange.generic;

/**
 * @author zhoujian
 * @Date 2018/7/6 15:42
 */
public class GenericFruit {
	static  class Fruit {
		@Override
		public String toString() {
			return "fruit";
		}
	}

	static  class Apple extends Fruit{
		@Override
		public String toString() {
			return "apple";
		}
	}

	static class Person{
		@Override
		public String toString() {
			return "Person";
		}
	}

	static class GenerateTest<T>{
		public void show_1(T t){
			System.out.println(t.toString());
		}

		public <E> void show_3(E t){
			System.out.println(t.toString());
		}

		public <T> void show_2(T t){
			System.out.println(t.toString());
		}
	}

	public static void main(String[] args){
		Apple apple = new Apple();
		Person person = new Person();

		GenerateTest<Fruit> generateTest = new GenerateTest<Fruit>();
		generateTest.show_1(apple);

		generateTest.show_2(person);
		generateTest.show_3(person);

		generateTest.show_3(apple);
		generateTest.show_2(apple);
	}
}
