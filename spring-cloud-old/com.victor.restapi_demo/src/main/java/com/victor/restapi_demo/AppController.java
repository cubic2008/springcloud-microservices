package com.victor.restapi_demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class AppController {
	
	@Autowired
	AppService appService;
	
	@RequestMapping(value="/greeting", method = RequestMethod.GET)
	public GreetingResponse getGreeting ( @RequestParam("pname") String pname ) {
		GreetingResponse resp = new GreetingResponse ( );
		resp.res = "Hello, " + pname;
		return resp;
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String sayHello ( ) {
		return "hello";
	}

	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public List<Student> getAllStudents ( ) {
		return this.appService.getAllStudents();
	}

	@RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
	public Student getStudentById ( @PathVariable("id") int id) {
		return this.appService.getStudentById(id);
	}

	@RequestMapping(value = "/students", method = RequestMethod.POST)
	public Student createStudent ( @RequestBody Student student) {
		this.appService.addStudent(student);
		return student;
	}

	@RequestMapping(value = "/students", method = RequestMethod.PUT)
	public Student updateStudent ( @RequestBody Student student) {
		this.appService.updateStudent(student);
		return student;
	}

	@RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
	public void removeStudent ( @PathVariable("id") int id ) {
		this.appService.deleteStudent(id);
	}


}
