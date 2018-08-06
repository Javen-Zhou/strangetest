package com.techsure.strange.lambda;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2018/7/16 17:13
 */
public class LambdaAnotherTest {

	public static void main(String[] args){

		TestVo testVo = new TestVo();

		System.out.println(getResult2(testVo));
	}

	public static String getResult2(TestVo testVo){
		return getResult(TestVo::getName);
	}

	public static String getResult(Crei crei){
		return crei.test();
	}

}

interface Crei{
	String test();
}

class TestVo{
	private static String name = "Amy";

	public static String getName() {
		return name;
	}

}
