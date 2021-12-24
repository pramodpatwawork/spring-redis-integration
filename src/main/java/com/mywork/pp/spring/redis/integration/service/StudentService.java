package com.mywork.pp.spring.redis.integration.service;

import java.security.SecureRandom;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mywork.pp.spring.redis.integration.model.Student;

@Service
public class StudentService {

	@Cacheable(value = "student", key = "#studentId")
	public Student getStudent(final String studentId) {
		try {
			Thread.sleep(10000);

			System.out.println("Executing: " + this.getClass().getSimpleName() + ".getStudent(" + studentId + ")");
			return new Student(studentId, Integer.toString(SecureRandom.getInstanceStrong().nextInt()), "ABC");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@CacheEvict(value = "student", key = "#studentId")
	public void updateStudent(String studentId, Student student) {
		System.out.println("Updating data base so removed cache: " + this.getClass().getSimpleName() + ".updateStudent("
				+ studentId + ")");
	}
}
