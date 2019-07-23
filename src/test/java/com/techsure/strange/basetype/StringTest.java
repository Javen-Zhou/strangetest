package com.techsure.strange.basetype;

import net.openhft.chronicle.core.Maths;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/8/27 星期一 11:49
 */
public class StringTest {
	private static final Logger logger = LoggerFactory.getLogger(StringTest.class);

	private volatile Integer num = 0;

	@Test
	public void testThread() throws InterruptedException {
		createThread();
		createThread();
		createThread();
		createThread();


	}

	private void createThread() {
		Thread thread = new Thread(() -> {
			boolean s = true;
			while(s) {
				synchronized (num) {
					num = num +1;
				}
				if(num >= 100){
					s = false;
				}
			}

		});
		thread.start();
	}


	@Test
	public void testLsitToString() {
		List<Long> metricIdList = new ArrayList<>();
		metricIdList.add(1L);
		metricIdList.add(2L);
		logger.info(metricIdList.toString());
	}

	@Test
	public void getStrLength() {
		String str = "INSERT INTO asm_double_5minute (metric_id,time,average_value,max_value,min_value,p5_value,p95_value,sum_value) VALUES ('674',1562897700000,14.8,7.0,4.0,4.0,5.0,24.0);";
		logger.info(str.length() + "");
	}

	@Test
	public void testStrSplit() {
		String[] arr = "metric.store".split(",");
		logger.info(arr.length + "");
	}

	@Test
	public void testAddress() {
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
		logger.info(String.valueOf(Maths.nextPower2(2 << 10, 8)));
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
