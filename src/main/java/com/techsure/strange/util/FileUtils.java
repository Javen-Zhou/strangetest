package com.techsure.strange.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhoujian
 * @Date 2018/12/5 18:07
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static Integer count(Pattern pattern,String filePath){
		int totalCount = 0,line = 0,tmpResult=0;
		File file = new File(filePath);
		try(BufferedReader bf = new BufferedReader(new FileReader(file))){
			String tmpString;
			while((tmpString = bf.readLine()) != null){
				line++;
				Matcher matcher = pattern.matcher(tmpString);
				while(matcher.find()){
					tmpResult = Integer.parseInt(matcher.group(1));
					if(tmpResult != line){
						totalCount++;
					}
				}

			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return totalCount;
	}

}
