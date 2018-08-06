package com.techsure.strange.simple;

/**
 * @author zhoujian
 * @Date 2018/8/6 11:28
 */
public class SimpleTest1 {
	public static void main(String[] args){
		String str1 = "主网IP";
		String str2 = "主网 IP";
		System.out.println(str1.replaceAll("\\s*", "").equals(str2.replaceAll("\\s*", "")));
	}
}
