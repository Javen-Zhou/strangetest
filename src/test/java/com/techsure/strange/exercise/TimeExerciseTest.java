package com.techsure.strange.exercise;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * @author zhoujian
 * @Date 2018/11/20 19:23
 */
public class TimeExerciseTest {

	private static final Logger logger = LoggerFactory.getLogger(TimeExerciseTest.class);

	@Test
	public void cacluateTime() {
		List<TimeVo> list = new ArrayList<>();
		list.add(new TimeVo().setStartDate("2018-10-10 09:00:01").setEndDate("2018-10-10 12:02:23"));
		list.add(new TimeVo().setStartDate("2018-10-10 08:12:03").setEndDate("2018-10-10 23:13:36"));
		list.add(new TimeVo().setStartDate("2018-10-10 14:12:43").setEndDate("2018-10-10 14:22:44"));
		list.add(new TimeVo().setStartDate("2018-10-10 22:12:23").setEndDate("2018-10-10 22:44:23"));
		list.add(new TimeVo().setStartDate("2018-10-10 05:12:23").setEndDate("2018-10-10 06:14:23"));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		list.sort((o1, o2) -> {
			Long time1, time2;
			try {
				time1 = sdf.parse(o1.getStartDate()).getTime();
				time2 = sdf.parse(o2.getStartDate()).getTime();

				if (time1 < time2) {
					return -1;
				}
				if (time1 > time2) {
					return 1;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return 0;

		});
		list.forEach(time -> logger.info("{},{}", time.getStartDate(), time.getEndDate()));

		TimeVo last, current;
		Long lastEndDate, currentStartDate, currentEndDate;
		for (int i = 1; i < list.size(); i++) {
			try {

				last = list.get(i - 1);
				current = list.get(i);

				lastEndDate = sdf.parse(last.getEndDate()).getTime();
				currentStartDate = sdf.parse(current.getStartDate()).getTime();
				currentEndDate = sdf.parse(current.getEndDate()).getTime();

				if (currentStartDate < lastEndDate) {
					if (lastEndDate < currentEndDate) {
						last.setEndDate(current.getEndDate());
					}
					list.remove(i);
					i--;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		logger.info("===========");
		list.forEach(time -> logger.info("{},{}", time.getStartDate(), time.getEndDate()));

		AtomicReference<Long> totalTime = new AtomicReference<>(0L);
		list.forEach(time -> {
			Long startTime, endTime;
			try {
				startTime = sdf.parse(time.getStartDate()).getTime();
				endTime = sdf.parse(time.getEndDate()).getTime();
				totalTime.updateAndGet(v -> v + endTime - startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
		logger.info(String.valueOf(Long.parseLong(totalTime.toString()) / MINUTES.toMillis(1)));
	}

}


class TimeVo {
	private String startDate;
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public TimeVo setStartDate(String startDate) {
		this.startDate = startDate;
		return this;
	}

	public String getEndDate() {
		return endDate;
	}

	public TimeVo setEndDate(String endDate) {
		this.endDate = endDate;
		return this;
	}
}
