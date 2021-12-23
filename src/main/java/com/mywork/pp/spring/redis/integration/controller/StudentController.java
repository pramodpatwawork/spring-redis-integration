package com.mywork.pp.spring.redis.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mywork.pp.spring.redis.integration.model.Student;
import com.mywork.pp.spring.redis.integration.service.StudentService;


@RestController
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/students/{id}")
	public Student findStudentById(@PathVariable String id) {
		System.out.println("Searching by ID  : " + id);
		return studentService.getStudent(id);
	}
}