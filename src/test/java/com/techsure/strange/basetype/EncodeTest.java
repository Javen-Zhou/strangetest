package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author zhoujian
 * @Date 2018/11/20 16:20
 */
public class EncodeTest {
	private static final Logger logger = LoggerFactory.getLogger(EncodeTest.class);

	@Test
	public void testUTF8() throws UnsupportedEncodingException {
		String s1 = "a编码测试";
		byte[] arr = s1.getBytes("UTF-8");
		for(int i=0;i<arr.length;i++){
			byte t1 = arr[i];
			System.out.println(Integer.toBinaryString((t1 & 0xFF) + 0x100).substring(1));
		}
	}
}
