package com.techsure.strange.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoujian
 * @Date 2018/9/18 星期二 15:06
 */
@RestController
public class TestApi {
	public static final Logger logger = LoggerFactory.getLogger(TestApi.class);

	@RequestMapping("/test")
	public void test(@RequestParam String name) {
		logger.info(name);
	}

}
