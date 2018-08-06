package com.techsure.strange.file;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoujian
 * @Date 2018/7/30 15:52
 */
public class JetWindowNumCountTest {
	public static void main(String[] args) {
		Integer totalCount = 0;
		//File file = new File("G:\\Data\\jet\\test\\0");
		File file = new File("G:\\Data\\jet\\1");
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

	private static String getLineDesc(String lineString) {
		String regex = ".*ts=(.*),.*key=\'([0-9]*)\'.*num=([0-9]*).*";
		Pattern pattern = Pattern.compile(regex);
		String lineDesc = "";
		Matcher m = pattern.matcher(lineString);
		while (m.find()) {
			lineDesc = "  ts=" + m.group(1) + ",key=" + m.group(2) + ",num=" + m.group(3);
		}
		return lineDesc;
	}

	private static Integer getNumInLine(String lineString) {
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
