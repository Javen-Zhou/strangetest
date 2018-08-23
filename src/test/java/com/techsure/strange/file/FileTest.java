package com.techsure.strange.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoujian
 * @Date 2018/8/20 21:36
 */
public class FileTest {
	private static final Logger logger = LoggerFactory.getLogger(FileTest.class);

	public void test(){
		Integer totalCount = 0;
		//File file = new File("G:\\Data\\jet\\test\\0");
		//File file = new File("F:\\Techsure\\ts-metric\\30data\\1");
		File file = new File("E:\\Data\\project\\ts-metric\\0");
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String tempString = null;
				Integer line = 1;
				while ((tempString = reader.readLine()) != null) {
					//System.out.println("line:" + line + " " + tempString);
					//System.out.println("line:" + line + " num= " + getNumInLine(tempString));
					System.out.println("line:" + line + getLineDesc(tempString));
					totalCount += getNumInLine(tempString);
					line++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(totalCount);
	}

	private String getLineDesc(String lineString) {
		String regex = ".*ts=(.*),.*key=\'([0-9]*)\'.*num=([0-9]*).*";
		Pattern pattern = Pattern.compile(regex);
		String lineDesc = "";
		Matcher m = pattern.matcher(lineString);
		while (m.find()) {
			lineDesc = "  ts=" + m.group(1) + ",key=" + m.group(2) + ",num=" + m.group(3);
		}
		return lineDesc;
	}

	private Integer getNumInLine(String lineString) {
		String regex = ".*num=([0-9]*).*";
		Pattern pattern = Pattern.compile(regex);
		String num = "";
		Matcher m = pattern.matcher(lineString);
		while (m.find()) {
			num = m.group(1);
			//System.out.println(m.group(1));
		}
		return Integer.valueOf(num);
	}
}
