package com.techsure.strange.simple;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2019/2/26 15:12
 */
public class EnumTest {
	private static final Logger logger = LoggerFactory.getLogger(EnumTest.class);

	@Test
	public void test1(){
		logger.info("" + TimeDiffEnum.valueOf("1HourAgo").getValue());
	}
}

enum TimeDiffEnum{
	ONE_HOUR_AGO("1HourAgo", TimeUnit.HOURS.toMillis(1));
	private String name;
	private Long value;

	TimeDiffEnum(String name, Long value) {
		this.name = name;
		this.value = value;
	}


	public String getName() {
		return name;
	}

	public Long getValue() {
		return value;
	}
}