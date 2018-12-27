package com.techsure.strange.hazelcast.jet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoujian
 * @Date 2018/12/4 18:41
 */
public class CountEvent {
	private static final Logger logger = LoggerFactory.getLogger(CountEvent.class);

	@Test
	public void countLostEvent(){
		File file = new File("E:\\Tmp\\event.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String tempString = null;
			Integer totalCount = 0;
			while ((tempString = reader.readLine()) != null) {
				totalCount += getEventCount(tempString);
			}
			logger.info("totalCount :{}",totalCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void countDataNum(){
		File file = new File("E:\\Tmp\\jet\\0");
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String tempString = null;
			Integer totalCount = 0;
			while ((tempString = reader.readLine()) != null) {
				totalCount += getDataCount(tempString);
			}
			logger.info("totalCount :{}",totalCount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Integer getEventCount(String line){
		Pattern pattern = Pattern.compile(".*\\[0.6\\]\\s([0-9]*)\\sevents\\slost\\sfor\\spartition.*");
		Matcher matcher = pattern.matcher(line);
		Integer count = 0;
		while(matcher.find()){
			count = Integer.valueOf(matcher.group(1));
		}
		return count;
	}



	private Integer getDataCount(String line){
		Pattern pattern = Pattern.compile(".*num=([0-9]*),.*");
		Matcher matcher = pattern.matcher(line);
		Integer count = 0;
		while(matcher.find()){
			count = Integer.valueOf(matcher.group(1));
		}
		return count;
	}
}
