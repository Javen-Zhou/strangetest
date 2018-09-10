package com.techsure.strange.file;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author zhoujian
 * @Date 2018/8/31 星期五 10:25
 */
public class FileOperateTest {
	private static final Logger logger = LoggerFactory.getLogger(FileOperateTest.class);


	@Test
	public void testDelete(){
		File file = new File("queue/rollup");
		if(file.exists()){
			logger.info("exist");
		}
		if(file.isDirectory()) {
			try {
				FileUtils.deleteDirectory(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
