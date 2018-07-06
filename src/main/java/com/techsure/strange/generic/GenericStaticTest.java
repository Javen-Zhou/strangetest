package com.techsure.strange.generic;

/**
 * @author zhoujian
 * @Date 2018/7/6 16:33
 */
public class GenericStaticTest {
	public static <T> StaticTest<T> test() {
		return new StaticTest<>();
	}

	static class StaticTest<T> {

		public String toString(T t) {
			System.out.println(t.toString());
			return t.toString();
		}
	}

	public static void main(String[] args){
		StaticTest<String> s = GenericStaticTest.<String>test();
		s.toString("333");
	}
}
