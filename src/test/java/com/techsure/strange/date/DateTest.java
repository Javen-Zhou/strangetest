package com.techsure.strange.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/29 星期三 15:58
 */
public class DateTest {
	private static final Logger logger = LoggerFactory.getLogger(DateTest.class);

	@Test
	public void testS(){
		logger.info(getEightHourFolderNameB(1535702400000L));
	}

	private String getEightHourFolderNameB(Long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		Integer day = c.get(Calendar.DAY_OF_WEEK);
		return String.valueOf(day - 2);
	}


	@Test
	public void testClosestHour(){
		Long time = 1535547601000L;
		Long count = time / TimeUnit.HOURS.toMillis(1);
		logger.info(DateFormatUtils.format(count * TimeUnit.HOURS.toMillis(1),"yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void testDateFormat() throws ParseException {
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			logger.info(getFormatFile(DateUtils.parseDate("2018-08-29 16:00:00", "yyyy-MM-dd HH:mm:ss").getTime()));
		}
		Long consume = System.currentTimeMillis() - begin;
		begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			logger.info(getFormatFile2(DateUtils.parseDate("2018-08-29 16:00:00", "yyyy-MM-dd HH:mm:ss").getTime()));
		}
		logger.info("end1:{},end2:{}", consume, System.currentTimeMillis() - begin);
	}

	@Test
	public void test1(){
		logger.info(1200000 % TimeUnit.MINUTES.toMillis(60) + "");
		logger.info(12 % 36 + "");
	}

	@Test
	public void testRange() throws ParseException {

		logger.info("fiveMinuteRange:{}",getFiveMinuteRange(DateUtils.parseDate("2018-08-29 00:05:00", "yyyy-MM-dd HH:mm:ss").getTime()));
		logger.info("fiveMinuteFolderName:{}",getFiveMinuteFolderName(DateUtils.parseDate("2018-08-29 01:05:00", "yyyy-MM-dd HH:mm:ss").getTime()));
		logger.info("oneHourRange:{}",getOneHourRange(DateUtils.parseDate("2018-08-29 24:00:00", "yyyy-MM-dd HH:mm:ss").getTime()));
		logger.info("oneHourFolderName:{}",getOneHourFolderName(DateUtils.parseDate("2018-08-29 16:05:00", "yyyy-MM-dd HH:mm:ss").getTime()));
		logger.info("eightHourRange:{}",getEightHourRange(DateUtils.parseDate("2018-08-29 24:00:00", "yyyy-MM-dd HH:mm:ss").getTime()));
		logger.info("eightHourFolderName:{}",getEightHourFolderName(DateUtils.parseDate("2018-08-29 24:00:00", "yyyy-MM-dd HH:mm:ss").getTime()));
	}



	@Test
	public void testConsume() {
		Long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			if (System.currentTimeMillis() == 0) {
				logger.info("test");
			}
		}
		logger.info("consume:{}", System.currentTimeMillis() - begin);
	}

	public static String getFormatFile(Long time) {
		Long dayTime = (time + TimeUnit.HOURS.toMillis(8)) % TimeUnit.HOURS.toMillis(24);
		Long hour = dayTime / TimeUnit.HOURS.toMillis(1);
		Long minute = dayTime % TimeUnit.SECONDS.toMillis(60);
		if (minute == 0) {
			return String.valueOf(hour - 1);
		} else {
			return String.valueOf(hour);
		}
	}

	public static String getFormatFile2(Long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		Integer hour = c.get(Calendar.HOUR_OF_DAY);
		Integer minute = c.get(Calendar.MINUTE);
		if (minute == 0) {
			return String.valueOf(hour - 1);
		} else {
			return String.valueOf(hour);
		}
	}

	String getFiveMinuteRange(Long time) {
		Long hourTime = (time + TimeUnit.HOURS.toMillis(8)) % TimeUnit.HOURS.toMillis(24);
		Long minuteTime = hourTime % TimeUnit.HOURS.toMillis(1);
		Long hour = hourTime / TimeUnit.HOURS.toMillis(1);
		if (hourTime == 0) {
			return String.valueOf(24);
		}
		if(minuteTime == 0){
			return String.valueOf(hour);
		}else{
			return String.valueOf(hour + 1);
		}
	}

	public String getOneHourRange(Long time) {
		Long hourTime = (time + TimeUnit.HOURS.toMillis(8)) % TimeUnit.HOURS.toMillis(24);
		Long count = hourTime / TimeUnit.HOURS.toMillis(8);
		Long hour = hourTime % TimeUnit.HOURS.toMillis(8);
		if (hourTime == 0) {
			count = 3L;
		}
		if (hour == 0) {
			count--;
		}
		return String.valueOf((count + 1) * 8);
	}

	String getEightHourRange(Long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		Integer day = c.get(Calendar.DAY_OF_WEEK);

		return String.valueOf(day - 2);
	}

	private String getFiveMinuteFolderName(Long time) {
		Long hourTime = (time + TimeUnit.HOURS.toMillis(8)) % TimeUnit.HOURS.toMillis(24);
		Long num = hourTime / TimeUnit.HOURS.toMillis(1);

		return String.valueOf(num == 0 ? 24 : num);
	}
	private String getOneHourFolderName(Long time) {
		Long hourTime = (time + TimeUnit.HOURS.toMillis(8)) % TimeUnit.HOURS.toMillis(24);
		Long num = hourTime / TimeUnit.HOURS.toMillis(8);
		return String.valueOf(num == 0 ? 24 : num * 8);
	}

	private String getEightHourFolderName(Long time) {
		//TODO
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		Integer day = c.get(Calendar.DAY_OF_WEEK);
		return String.valueOf(day - 2);
	}
}
