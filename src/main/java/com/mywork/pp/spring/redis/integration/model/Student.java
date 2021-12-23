package com.mywork.pp.spring.redis.integration.model;

public class Student {
	String studentId;
	String name;
	String clz;

	public Student(String studentId, String name, String clz) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.clz = clz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClz() {
		return clz;
	}

	public void setClz(String clz) {
		this.clz = clz;
	}

}