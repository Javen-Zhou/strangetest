package com.techsure.strange.loop;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author zhoujian
 * @Date 2018/8/23 20:37
 */
public class LoopTest {

	private static final Logger logger = LoggerFactory.getLogger(LoopTest.class);

	@Test
	public void test() {
		List<Integer> list = new ArrayList<>();
		list.add(0);
		list.add(128);
		list.add(512);
		list.add(768);
		//Integer num = 1;
		compute(list);
		/*for (int i = 0; i < num; i++) {
			compute(list);
		}*/

		//Set<Integer> set = new HashSet<>(list);
		//logger.info(String.valueOf(set.size()));
		//list.sort(Integer::compareTo);
		list.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 == o2) {
					return 0;
				}
				return o1 < o2 ? -1 : 1;
			}
		});
		list.forEach(place -> logger.info(String.valueOf(place)));


		/*Map<String, LoopPlace> tempMap = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			LoopPlace loopPlace = new LoopPlace();
			loopPlace.setAddress("192.168.0." + i + ":8080");
			loopPlace.setPlace(list.get(i));
			tempMap.put(loopPlace.getAddress(), loopPlace);
		}
		List<LoopPlace> tempList = getReadyList(tempMap);
		tempList.forEach(place -> logger.info("address:{},place:{}", place.getAddress(), place.getPlace()));*/
	}

	private static final String ADDRESS = "address";
	private static final String START = "start";
	private static final String END = "end";

	@Test
	public void testRange() throws IOException {
		JSONObject res = new JSONObject();
		List<JSONObject> pointList = new ArrayList<>();
		List<LoopPlace> readyLoopList = new ArrayList<>();
		List<Integer> list = new ArrayList<>();
		/*for (int i = 0; i < 3; i++) {
			compute(list);
		}*/

		list.add(0);
		list.add(256);
		for (int i = 0; i < list.size(); i++) {
			LoopPlace loopPlace = new LoopPlace();
			loopPlace.setPlace(list.get(i));
			loopPlace.setAddress("192.168.0." + i);
			readyLoopList.add(loopPlace);
		}

		sort(readyLoopList);

		readyLoopList.forEach(loopPlace -> logger.info("address:{},place:{}", loopPlace.getAddress(), loopPlace.getPlace()));

		Integer size = readyLoopList.size();
		JSONObject pointMessage;
		LoopPlace loopPlace;
		Boolean startFromZero = false;
		Boolean endWithBoundary = false;
		if (size == 1) {
			startFromZero = true;
			endWithBoundary = true;
		}

		for (int i = 0; i < size ; i++) {
			pointMessage = new JSONObject();
			loopPlace = readyLoopList.get(i);
			if (i == 0 && loopPlace.getPlace() != 0) {
				startFromZero = true;
			}

			if (i + 1 == size) {
				endWithBoundary = true;
			}
			pointMessage.put(ADDRESS, loopPlace.getAddress());
			pointMessage.put(START, startFromZero ? 0 : loopPlace.getPlace());
			pointMessage.put(END, endWithBoundary ? 1024 : readyLoopList.get(i + 1).getPlace());
			pointList.add(pointMessage);
			startFromZero = false;
			endWithBoundary = false;
		}

		res.put("response",pointList);
		logger.info(res.toString());
	}

	private static List<LoopPlace> sort(List<LoopPlace> loopPlaceList) {
		loopPlaceList.sort((o1, o2) -> {
			if (o1.getPlace() == o2.getPlace()) {
				return 0;
			}
			return o1.getPlace() < o2.getPlace() ? -1 : 1;
		});
		return loopPlaceList;
	}

	private void compute(List<Integer> list) {
		list.sort((o1, o2) -> {
			if (o1 == o2) {
				return 0;
			}
			return o1 < o2 ? -1 : 1;
		});
		Integer size = list.size();
		if (size == 0 || list.get(0) != 0) {
			list.add(0);
		} else {
			Integer maxDelta = 0;
			Integer delta;
			Integer cursor = list.get(0);

			for (int i = 0; i < size; i++) {
				Integer n = list.get(i);
				Integer m = i + 1 == size ? 1024 : list.get(i + 1);
				delta = m - n;
				if (delta > maxDelta) {
					maxDelta = delta;
					cursor = m;
				}
			}

			Integer newPlace = cursor - maxDelta / 2;
			list.add(newPlace);
		}
	}

	private static List<LoopPlace> getReadyList(Map<String, LoopPlace> loopPlaceMap) {
		List<LoopPlace> resultList = new ArrayList<>();
		Map<String, LoopPlace> tempMap = new HashMap<>(loopPlaceMap);
		for (LoopPlace loopPlace : tempMap.values()) {
			if (resultList.isEmpty()) {
				resultList.add(loopPlace);
			} else {
				Boolean isInsert = false;
				for (int i = 0; i < resultList.size(); i++) {
					if (loopPlace.getPlace() < resultList.get(i).getPlace()) {
						resultList.add(i, loopPlace);
						isInsert = true;
						break;
					}
				}
				if (!isInsert) {
					resultList.add(loopPlace);
				}
			}
		}
		return resultList;

	}
}
