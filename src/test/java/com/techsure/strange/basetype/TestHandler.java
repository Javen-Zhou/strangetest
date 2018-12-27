package com.techsure.strange.basetype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoujian
 * @Date 2018/12/5 17:42
 */
public class TestHandler {
	private static final Logger logger = LoggerFactory.getLogger(TestHandler.class);

	private static BlockingQueue<Integer> queue = new LinkedBlockingDeque<Integer>(100000);
	private File file = new File("E:\\Tmp\\handler.txt");
	private FileWriter fw;

	public void doAction(Integer num) {
		try {
			//TimeUnit.SECONDS.sleep(1L);
			Integer result = ++num;
			queue.put(result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void start() {
		try {
			fw = new FileWriter(file);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		Thread thread = new Thread(() -> {
			while (true) {
				try {
					Integer result = queue.take();
					fw.write("result:" + result);
					//fw.write(result);
					fw.write("\n");
				} catch (InterruptedException | IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
		thread.start();
	}

	public void stop() throws IOException, InterruptedException {
		TimeUnit.SECONDS.sleep(5L);
		fw.flush();
		fw.close();
	}
}
