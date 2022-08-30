package com.victor.restapi_demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AppService {
	
	private static Map<Integer, Student> studentList = new HashMap<>();
	
	static {
		studentList.put(1,  new Student ( 1, "John", 20 ) );
		studentList.put(2,  new Student ( 2, "Mary", 30 ) );
		studentList.put(3,  new Student ( 3, "Victor", 22 ) );
	}
	
	public List<Student> getAllStudents ( ) {
		return new ArrayList<> ( studentList.values() );
	}
	
	public void addStudent ( Student student ) {
		studentList.put( student.getId(), student );
	}
	
	public void updateStudent ( Student student ) {
		addStudent ( student );
	}
	
	public void deleteStudent ( int id ) {
		studentList.remove( id );
	}
	
	public Student getStudentById ( int id ) {
		return studentList.get(id);
	}

}
