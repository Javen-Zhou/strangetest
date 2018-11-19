package com.techsure.strange.basetype;

import net.openhft.chronicle.core.Maths;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2018/8/27 星期一 11:49
 */
public class StringTest {
	private static final Logger logger = LoggerFactory.getLogger(StringTest.class);

	@Test
	public void testAddress(){
		{
			String s = "a word";
			logger.info(String.valueOf(s.hashCode()));
		}

		{
			String s2 = "a word";
			logger.info(String.valueOf(s2.hashCode()));
		}
	}

	@Test
	public void test1() {
		logger.info(String.valueOf(2 << 10));
		logger.info(String.valueOf(Maths.nextPower2(2<<10, 8)));
	}

	@Test
	public void testString() {
		String temp = null;
		if (StringUtils.isNotBlank(temp)) {
			logger.info(temp + "test");
		}
	}

	@Test
	public void testString2() {
		String temp = null;
		if (StringUtils.isNotBlank(temp)) {

		}
	}

	private void testMethod(String value) {
		String temp = null;
		switch (value) {
			case "1":
				temp = "1";
				break;
			case "2":
				temp = "2";
				break;
			default:
				break;
		}
		if (StringUtils.isNotBlank(temp)) {
			logger.info(temp + "test");
		}
	}
}
