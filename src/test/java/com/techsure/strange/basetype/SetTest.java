package com.techsure.strange.basetype;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhoujian
 * @Date 2018/12/10 18:08
 */
public class SetTest {
	private static final Logger logger = LoggerFactory.getLogger(SetTest.class);

	@Test
	public void testSet(){
		Set<Long> set = new HashSet<>();
		set.add(1L);
		set.add(2L);
		set.clear();
		logger.info(String.valueOf(set.size()));
	}
}
