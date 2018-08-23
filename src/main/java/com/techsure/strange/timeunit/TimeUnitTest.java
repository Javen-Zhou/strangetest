package com.techsure.strange.timeunit;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/7/9 16:50
 */
public class TimeUnitTest {
	private static final Logger logger = LoggerFactory.getLogger(TimeUnitTest.class);

	public static void main(String[] args) {
		logger.info(DateFormatUtils.format(0L,"yyyy-MM-dd HH:mm:ss"));
		logger.info(String.valueOf(TimeUnit.SECONDS.toMillis(1)));
		logger.info(String.valueOf(TimeUnit.MINUTES.toMillis(1)));
		logger.info(String.valueOf(TimeUnit.HOURS.toMillis(1)));
		logger.info(String.valueOf(TimeUnit.DAYS.toMillis(1)));
	}
}
