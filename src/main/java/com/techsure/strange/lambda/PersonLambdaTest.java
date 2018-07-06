package com.techsure.strange.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zhoujian
 * @Date 2018/7/6 17:20
 */
public class PersonLambdaTest {
	private static List<Person> javaProgrammers;
	private static List<Person> phpProgrammers;
	static {
		javaProgrammers = new ArrayList<Person>() {
			{
				add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
				add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
				add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
				add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
				add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
				add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
				add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
				add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
				add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
				add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
			}
		};

		phpProgrammers = new ArrayList<Person>() {
			{
				add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
				add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
				add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
				add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
				add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
				add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
				add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
				add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
				add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
				add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));
			}
		};
	}

	public static void  main(String[] args){
		System.out.println("所有程序员的姓名:");
		javaProgrammers.forEach((p) -> System.out.printf("%s %s;",p.getFirstName(),p.getLastName()));
		phpProgrammers.forEach((p) -> System.out.printf("%s %s;",p.getFirstName(),p.getLastName()));

		System.out.println("给程序员加薪 5% :");
		Consumer<Person> giveRaise = e -> {e.setSalary(e.getSalary() /100 * 5 + e.getSalary());System.out.println(e.getSalary());};

		javaProgrammers.forEach(giveRaise);
		phpProgrammers.forEach(giveRaise);

		System.out.println("下面是月薪超过 $1400的PHP程序员:");
		phpProgrammers.stream().filter((p) -> (p.getSalary() > 1400))
				.forEach((p) -> System.out.printf("%s %s;",p.getFirstName(),p.getLastName()));

		System.out.println("\n最前面的3个java开发：");
		javaProgrammers.stream().limit(3)
				.forEach((p) -> System.out.printf("%s %s;",p.getFirstName(),p.getLastName()));

		System.out.println("\n排序：");
		javaProgrammers.stream().sorted((p,p2) -> (p.getFirstName().compareTo(p2.getFirstName())))
				.forEach((p) -> System.out.printf("%s %s; %n",p.getFirstName(),p.getLastName()));
	}
}
