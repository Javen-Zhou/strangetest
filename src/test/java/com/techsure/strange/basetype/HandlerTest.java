package com.techsure.strange.basetype;

import com.techsure.strange.util.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author zhoujian
 * @Date 2018/12/5 17:47
 */
public class HandlerTest {
	private static final Logger logger = LoggerFactory.getLogger(HandlerTest.class);


	@Test
	public void doTest() throws IOException, InterruptedException {
		TestHandler handler = new TestHandler();
		handler.start();
		for (int i = 0; i < 2000000; i++) {
			handler.doAction(i);
		}
		handler.stop();
		int count = FileUtils.count(Pattern.compile(".*result:([0-9]*).*"), "E:\\Tmp\\handler.txt");
		logger.info(String.valueOf(count));
	}
}
