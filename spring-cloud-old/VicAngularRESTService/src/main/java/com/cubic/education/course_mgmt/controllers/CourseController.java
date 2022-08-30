package com.cubic.education.course_mgmt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cubic.education.course_mgmt.domain.Course;
import com.cubic.education.course_mgmt.services.CourseService;

@RestController
@RequestMapping("/vic-angular")
@CrossOrigin("*")
public class CourseController {
	
	@RequestMapping ( value = "/name", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String name ( ) {
		return "Course Controller";
	}
	
	@Autowired
	private CourseService service;
	
	@RequestMapping ( value = "/courses", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Course> getCourses ( ) {
//		return new Course ( 1, "Angular" );
		return this.service.getAllCourses();
	}
	
	@RequestMapping ( value = "/courses/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Course getCourse ( @PathVariable("id") int courseId ) {
		
		return this.service.getCourseById ( courseId );
		
	}
	
	@RequestMapping ( value = "/courses", method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Course createCourse ( @RequestBody Course course ) {
		return this.service.createNewCourse ( course );
	}
	
	@RequestMapping ( value = "/courses/{id}", method=RequestMethod.PUT, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void updateCourse ( @PathVariable("id") int courseId, @RequestBody Course course ) {
		this.service.updateCourse ( courseId, course );
	}

	@RequestMapping ( value = "/courses/{id}", method=RequestMethod.DELETE)
	public void deleteCourse ( @PathVariable("id") int courseId ) {
		this.service.deleteCourse ( courseId );
	}
	
	@RequestMapping ( value = "/courses/search", produces=MediaType.APPLICATION_JSON_UTF8_VALUE )
	public List<Course> searchCourse ( @RequestParam("term") String term ) {
		System.out.println( "Search request received: term = " + term );
		return this.service.searchCourse ( term );
		
	}

	public CourseService getService() {
		return service;
	}

	public void setService(CourseService service) {
		this.service = service;
	}

	
}
