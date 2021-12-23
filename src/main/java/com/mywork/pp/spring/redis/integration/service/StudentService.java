package com.mywork.pp.spring.redis.integration.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mywork.pp.spring.redis.integration.model.Student;

@Service
public class StudentService {

    @Cacheable(value="student", key="#studentId")
    public Student getStudent( final String studentId ) {
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("Executing: " + this.getClass().getSimpleName() + ".getStudent(" + studentId + ")");
        return new Student(studentId, "XYZ", "ABC");
    }
}
