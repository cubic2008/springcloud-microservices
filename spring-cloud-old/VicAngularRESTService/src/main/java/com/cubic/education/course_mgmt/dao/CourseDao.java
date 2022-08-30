package com.cubic.education.course_mgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cubic.education.course_mgmt.domain.Course;

@Repository
public class CourseDao {

	@Autowired
	private JdbcTemplate jdbctemplate;

	public List<Course> retrieveAllCourses() {

		return this.jdbctemplate.query("SELECT ID, NAME FROM COURSE", 
//				new RowMapper<Course>() {
//					@Override
//					public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
//						
//						return new Course ( 
//								rs.getInt( "ID" ),
//								rs.getString( "NAME" ) ));
//					}
//		});
				(rs, rowNum) -> new Course ( rs.getInt( "ID" ), rs.getString( "NAME" ) )
			);
	}

	public JdbcTemplate getJdbctemplate() {
		return jdbctemplate;
	}

	public void setJdbctemplate(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public Course retrieveCourse(int courseId) {
		return this.jdbctemplate.queryForObject(
				"SELECT ID, NAME FROM COURSE WHERE ID = ?", 
				new Object[]{ courseId }, 
				(rs, rowNum) -> new Course ( rs.getInt( "ID" ), rs.getString( "NAME" ) ) );
	}

	public Course saveCourse(Course course) {
		System.out.println( "course = " + course );
		KeyHolder holder = new GeneratedKeyHolder();
		this.jdbctemplate.update ( 
			connection -> {
				PreparedStatement ps = connection.prepareStatement ( 
						course.getId() == 0 ? "INSERT INTO COURSE(NAME) VALUES (?)" : "UPDATE COURSE SET NAME = ? WHERE ID = ?", 
						Statement.RETURN_GENERATED_KEYS );
				ps.setString(1, course.getName());
				if ( course.getId() > 0 ) {
					ps.setInt(2, course.getId());
				}
				return ps;
			}, holder);

		if ( course.getId() == 0 ) {
			int newCourseId = holder.getKey().intValue();
			course.setId( newCourseId );
		}
		return course;
	}

	public void deleteCourse(int courseId) {
		this.jdbctemplate.update ( "DELETE FROM COURSE WHERE ID = ?", courseId );
	}

	public List<Course> searchCourse(String term) {
		return this.jdbctemplate.query("SELECT ID, NAME FROM COURSE WHERE NAME LIKE ?", new String[] { "%" + term + "%" },
				(rs, rowNum) -> new Course ( rs.getInt( "ID" ), rs.getString( "NAME" ) )
			);
	}
	
	
}
