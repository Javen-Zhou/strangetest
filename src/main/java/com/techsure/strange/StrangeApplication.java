package com.techsure.strange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhoujian
 * @Date 2018/9/18 星期二 15:04
 */
@SpringBootApplication
@ComponentScan
public class StrangeApplication {
	public static void main(String[] args) {
		SpringApplication.run(StrangeApplication.class, args);
	}
}
