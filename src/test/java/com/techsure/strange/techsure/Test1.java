package com.techsure.strange.techsure;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2019/7/12 14:27
 */
public class Test1 {
	private static final Logger logger = LoggerFactory.getLogger(Test1.class);

	@Test
	public void getDate() {
//		String dateStr = "2019-07-12 14:20:00";
		String dateStr = "2019-07-12 20:20:00";
//		String dateStr = "2019-07-12 12:20:00";
//		String dateStr = "2018-11-11 09:20:00";
//		String dateStr = "2019-07-12 09:20:00";
		try {
			Date date = DateUtils.parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int wek = cal.get(Calendar.DAY_OF_WEEK);

			long activeTime = TimeUnit.HOURS.toMillis(12);
			long time = date.getTime();
			long dayStartTime = getEndOfDay(time) - TimeUnit.HOURS.toMillis(15);

			if (dayStartTime < time && wek != 1 && wek != 7) {
				long left = time - dayStartTime;
				if (left > 0 && left <= TimeUnit.HOURS.toMillis(3)) {
					activeTime = activeTime + left;
				} else if (left > TimeUnit.HOURS.toMillis(3) && left < TimeUnit.HOURS.toMillis(9)) {
					activeTime = activeTime + (left - TimeUnit.HOURS.toMillis(5) < 0 ? 0 : (left - TimeUnit.HOURS.toMillis(5))) + TimeUnit.HOURS.toMillis(3);
				} else if (left > TimeUnit.HOURS.toMillis(9) && left < TimeUnit.HOURS.toMillis(14)) {
					activeTime = activeTime + (left - TimeUnit.HOURS.toMillis(11) < 0 ? 0 : (left - TimeUnit.HOURS.toMillis(11)))+ TimeUnit.HOURS.toMillis(7);
				}
			}

			long endTime = getNext(dayStartTime, activeTime, wek);
			logger.info(DateFormatUtils.format(endTime, "yyyy-MM-dd HH:mm:ss"));

		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Long getEndOfDay(Long time) {
		return ((time + TimeUnit.HOURS.toMillis(8)) / TimeUnit.DAYS.toMillis(1) + 1) * TimeUnit.DAYS.toMillis(1) - TimeUnit.HOURS.toMillis(8);
	}

	private Long getNext(long currentTime, long activeTime, int wek) {
		if (wek > 7) {
			wek = wek % 7;
		}

		if (wek == 1 || wek == 7) {
			wek++;
			currentTime = ((currentTime + TimeUnit.HOURS.toMillis(8)) / TimeUnit.DAYS.toMillis(1) + 1) * TimeUnit.DAYS.toMillis(1) + TimeUnit.HOURS.toMillis(1);
			return getNext(currentTime, activeTime, wek);
		}

		if (activeTime > 0 && activeTime <= TimeUnit.HOURS.toMillis(3)) {
			currentTime += activeTime;
		} else if (activeTime > TimeUnit.HOURS.toMillis(3) && activeTime < TimeUnit.HOURS.toMillis(7)) {
			currentTime += activeTime + TimeUnit.HOURS.toMillis(2);
		} else if (activeTime > TimeUnit.HOURS.toMillis(7) && activeTime < TimeUnit.HOURS.toMillis(10)) {
			currentTime += activeTime + TimeUnit.HOURS.toMillis(4);
		} else {
			activeTime = activeTime - TimeUnit.HOURS.toMillis(10);
			currentTime += TimeUnit.DAYS.toMillis(1);
			wek++;
			return getNext(currentTime, activeTime, wek);
		}
		return currentTime;
	}
}
