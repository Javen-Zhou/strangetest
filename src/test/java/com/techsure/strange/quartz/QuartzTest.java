package com.techsure.strange.quartz;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.CronCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2019/6/18 14:33
 */
public class QuartzTest {
	private static final Logger logger = LoggerFactory.getLogger(QuartzTest.class);

	@Test
	public void testReplace(){
		String cron = "0 20-31 12-13 * * ? *";
		logger.info(cron.replaceFirst("0","*"));
	}


	@Test
	public void testQuartz() throws SchedulerException, InterruptedException, ParseException {
		JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity("test").build();
		SimpleTrigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("myTrigger")
				.modifiedByCalendar("ca")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever())
				.build();
		StdSchedulerFactory factory = new StdSchedulerFactory();
		Scheduler scheduler = factory.getScheduler();
		CronCalendar calendar = new CronCalendar("0/3 * 14-15 * * ? *");
		scheduler.addCalendar("ca", calendar, false, false);
		scheduler.scheduleJob(jobDetail,trigger);
		scheduler.start();

		while(true){
			TimeUnit.SECONDS.sleep(2L);
		}
	}
}


