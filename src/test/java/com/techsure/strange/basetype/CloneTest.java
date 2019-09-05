package com.techsure.strange.basetype;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujian
 * @Date 2019/7/3 18:44
 */
public class CloneTest {
	private static final Logger logger= LoggerFactory.getLogger(CloneTest.class);

	@Test
	public void testNullAttr(){
		Integer age = new Student().getAge();
		if(age == null){
			logger.info("access");
		}
	}
	
	@Test
	public void testClone(){
		Course courseA = new Course();
		courseA.setName("Math");
		courseA.setGrade(100);
		Student st1 = new Student();
		st1.setUserName("aaaa");
		st1.setAge(18);
		st1.setCourse(courseA);
		
		Student st2 = (Student) st1.clone();
		logger.info(st1.hashCode() + "");
		logger.info(st2.hashCode() + "");

		logger.info("------------course hashcode");
		logger.info(st1.getCourse().hashCode() + "");
		logger.info(st2.getCourse().hashCode() + "");

		st1.setAge(20);
		logger.info(st1.toString());
		logger.info(st2.toString());

		st1.getCourse().setGrade(80);
		st2.getCourse().setGrade(70);
		logger.info(st1.toString());
		logger.info(st2.toString());
	}
	
}

class Student implements Cloneable{
	private String userName;
	private Integer age;
	private Course course;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public Object clone() {
		Student stu = null;
		try{
			stu = (Student)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		stu.course = (Course) course.clone();
		return stu;
	}

	@Override
	public String toString() {
		return "Student{" +
				"userName='" + userName + '\'' +
				", age=" + age +
				", course=" + course +
				'}';
	}
}

class Course implements Cloneable{
	private String name;
	private Integer grade;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	@Override
	public Object clone() {
		Course stu = null;
		try{
			stu = (Course)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return stu;
	}

	@Override
	public String toString() {
		return "Course{" +
				"name='" + name + '\'' +
				", grade=" + grade +
				'}';
	}
}