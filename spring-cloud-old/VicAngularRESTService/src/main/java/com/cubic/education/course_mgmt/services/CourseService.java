package com.cubic.education.course_mgmt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubic.education.course_mgmt.dao.CourseDao;
import com.cubic.education.course_mgmt.domain.Course;

@Service
public class CourseService {
	
	@Autowired
	private CourseDao dao;
	
	public List<Course> getAllCourses ( ) {
		return this.dao.retrieveAllCourses();
	}

	public CourseDao getDao() {
		return dao;
	}

	public void setDao(CourseDao dao) {
		this.dao = dao;
	}

	public Course getCourseById(int courseId) {
		return this.dao.retrieveCourse ( courseId );
	}

	public Course createNewCourse(Course course) {
		course.setId( 0 );
		return this.dao.saveCourse ( course );
	}

	public void updateCourse(int courseId, Course course) {
		course.setId( courseId );
		this.dao.saveCourse(course);
	}

	public void deleteCourse(int courseId) {
		this.dao.deleteCourse ( courseId );
		
	}

	public List<Course> searchCourse(String term) {
		return this.dao.searchCourse ( term );
	}
	
}
