package com.mywork.pp.spring.redis.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Void> updateStudent(@PathVariable String id, @RequestBody Student student) {
		System.out.println("Searching by ID  : " + id);
		studentService.updateStudent(id, student);
		return new ResponseEntity<Void>( HttpStatus.OK );

	}
}