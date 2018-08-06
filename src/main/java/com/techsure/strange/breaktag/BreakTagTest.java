package com.techsure.strange.breaktag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujian
 * @Date 2018/7/19 12:10
 */
public class BreakTagTest {
	public static void main(String[] args) {
		List<Map> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Map map = new HashMap<>();
			map.put("key", "key");
			list.add(map);
		}
		RESULT:
		for(int i=0;i<5;i++) {
			for (Map map : list) {
				if (map.containsKey("vale")) {

				} else {
					System.out.println("1");
					continue RESULT;
				}
			}
		}
		System.out.println("end");

		int count =0;
		Result:for(int i=0;i<5;i++) {
			while (count < 5) {
				count++;
				continue Result;
			}
		}
	}
}
