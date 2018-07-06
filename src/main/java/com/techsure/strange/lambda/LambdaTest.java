package com.techsure.strange.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhoujian
 * @Date 2018/7/6 16:54
 */
public class LambdaTest {
	public static void main(String[] args){
		String[] atp = {"Rafael Nadal", "Novak Djokovic",
				"Stanislas Wawrinka",
				"David Ferrer","Roger Federer",
				"Andy Murray","Tomas Berdych",
				"Juan Martin Del Potro"};
		List<String> players =  Arrays.asList(atp);

		players.forEach((player) -> System.out.println(player));
		players.forEach(System.out::println);
	}
}
