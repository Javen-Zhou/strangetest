package com.techsure.strange.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author zhoujian
 * @Date 2018/7/16 16:36
 */
public class PersonService {
	private List<Person> list = new ArrayList<>();

	public List<Person> findByName(String name){
		return find(p -> name.equalsIgnoreCase(p.getFirstName())?0:1);
		//Criteria::test2;
	}


	public List<Person> find(Criteria<Person> Criteria){
		List<Person> people = new ArrayList<>();
		for(Person p:list){
			if(Criteria.test(p) == 0){
				people.add(p);
			}
		}
		return people;
	}
}

interface Criteria<T>{
	Integer test(T t);
}
