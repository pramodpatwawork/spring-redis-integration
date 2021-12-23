package com.mywork.pp.spring.redis.integration.model;

import java.io.Serializable;

public class Student implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8397881299181647488L;
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