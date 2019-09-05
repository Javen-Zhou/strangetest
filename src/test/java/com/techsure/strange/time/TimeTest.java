package com.techsure.strange.time;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/16 15:49
 */
public class TimeTest {

	private static final Logger logger = LoggerFactory.getLogger(TimeTest.class);


	@Test
	public void testFormat(){
		logger.info(DateFormatUtils.format(new Date(),"HHmmss"));
	}

	@Test
	public void testGetEightHourFolderName() throws ParseException {
		String str = "2018-12-17 24:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(str);
		long time = date.getTime();


		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time - TimeUnit.HOURS.toMillis(8));
		Integer day = c.get(Calendar.DAY_OF_WEEK);
		logger.info(day - 1 + "");
	}

	@Test
	public void testPdOneDay() throws ParseException {
		String str = "2018-12-17 8:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(str);
		long time = date.getTime();

		logger.info((time + TimeUnit.HOURS.toMillis(8)) % TimeUnit.DAYS.toMillis(1) == 0 ? "yes" : "no");
	}

	@Test
	public void testGetClosestFiveMinute() throws ParseException {

		Long ctime = getTime("2018-12-10 17:59:00", "yyyy-MM-dd HH:mm:ss");
		Long nextTime = getClosestFiveMinute(ctime);
		logger.info(DateFormatUtils.format(nextTime, "yyyy-MM-dd HH:mm:ss"));
	}

	private Long getClosestFiveMinute(Long ctime) {
		return ctime / TimeUnit.MINUTES.toMillis(5) * TimeUnit.MINUTES.toMillis(5) + TimeUnit.MINUTES.toMillis(5);
	}

	private Long getTime(String dateStr, String formatStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.parse(dateStr).getTime();
	}

	@Test
	public void testMill() {
		logger.info(TimeUnit.SECONDS.toMillis(60) + "");
	}

	@Test
	public void testTime() {
		Long time = 1536019200000L % TimeUnit.HOURS.toMillis(8);
		logger.info(String.valueOf(time));
	}

	@Test
	public void testTimeZone() throws ParseException {
		ZoneId zoneId = ZoneId.of("UTC");
		Long time = 1534377884000L;
		String formatPattern = "yyyy-MM-dd HH:mm:ss";
		logger.info(DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone(zoneId)));

		logger.info(String.valueOf(new Date()));
		LocalDateTime now = LocalDateTime.now(zoneId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		logger.info(now.format(formatter));
	}

	@Test
	public void testTimeRound() throws ParseException {
		Long time = 1534377884000L;
		String timeStr = "2018-08-16 18:04:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		logger.info(DateFormatUtils.format(getRoundTime(time, TimeUnit.HOURS.toMillis(1)), "yyyy-MM-dd HH:mm:ss"));
		logger.info(DateFormatUtils.format(getRoundTime(sdf.parse(timeStr).getTime(), TimeUnit.MINUTES.toMillis(5)), "yyyy-MM-dd HH:mm:ss"));
	}

	private Long getRoundTime(Long time, Long round) {
		Long toCalculateTime = time % round;
		Long baseTime = time - toCalculateTime;
		Integer num = round.intValue() / Long.valueOf(TimeUnit.HOURS.toMillis(24)).intValue();
		if (toCalculateTime < round / 2) {
			return baseTime - (num == 1 ? TimeUnit.HOURS.toMillis(8) : 0);
		} else {
			return baseTime + round - (num == 1 ? TimeUnit.HOURS.toMillis(8) : 0);
		}
	}

	@Test
	public void test() {
		// TODO Auto-generated method stub
		String time1 = "08:12";
		String time2 = "07:56";
		String time3 = "12:56";
		String time4 = "23:56";

		String time11 = getInitialTime(time1);
		String time22 = getInitialTime(time2);
		String time33 = getInitialTime(time3);
		String time44 = getInitialTime(time4);

		System.out.println(time11);
		System.out.println(time22);
		System.out.println(time33);
		System.out.println(time44);

	}

	/**
	 * 时间就近取整:<=30分向前取整,>30分向后取整
	 * Author:zr
	 *
	 * @param time inTime 07:56
	 * @return outTime 08:00
	 */

	private static String getInitialTime(String time) {
		String hour = "00";//小时
		String minutes = "00";//分钟
		String outTime = "00:00";
		StringTokenizer st = new StringTokenizer(time, ":");
		List<String> inTime = new ArrayList<String>();
		while (st.hasMoreElements()) {
			inTime.add(st.nextToken());
		}
		hour = inTime.get(0).toString();
		minutes = inTime.get(1).toString();
		if (Integer.parseInt(minutes) > 30) {
			hour = (Integer.parseInt(hour) + 1) + "";
		}
		outTime = hour + ":00";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		try {
			outTime = sdf.format(sdf.parse(outTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outTime;
	}

}