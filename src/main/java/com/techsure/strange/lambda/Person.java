package com.techsure.strange.lambda;

/**
 * @author zhoujian
 * @Date 2018/7/6 17:19
 */
public class Person {
	private String firstName,lastName,job,gender;
	private int salary,age;

	public Person(String firstName, String lastName, String job,
				  String gender, int age, int salary) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.job = job;
		this.gender = gender;
		this.salary = salary;
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
