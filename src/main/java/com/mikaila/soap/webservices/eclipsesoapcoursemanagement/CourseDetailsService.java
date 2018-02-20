package com.mikaila.soap.webservices.eclipsesoapcoursemanagement;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CourseDetailsService {
	
	public enum Status{
		SUCCESS, FAILURE
	}
	
	private static List<Course>courses = new ArrayList<>();
	//create a static list of courses
	static {
		Course course1 = new Course(1,"spring","Learn Spring 101");
		courses.add(course1);
		
		Course course2 = new Course(2,"springMVC","Learn Spring MVC");
		courses.add(course2);
		
		Course course3 = new Course(3,"springBoot","Learn Spring Boot");
		courses.add(course3);
		
		Course course4 = new Course(4,"Maven","Learn about Maven");
		courses.add(course4);
		
	}
	
	//get all course details 
		public List<Course> findAll(){
			return courses;
		}
		

	//Get course details by id 
	public Course findById(int id) {
		for(Course course: courses) {
			if(course.getId() == id) {
				return course;
			}
		}
		return null;
	}
	
	
	//delete a course by id
	public Status deleteById(int id) {
		Iterator<Course>iterator = courses.iterator();
		while(iterator.hasNext()) {
			Course course = iterator.next();
			if(course.getId() == id) {
				iterator.remove();
				return Status.SUCCESS; //meaning its deleted successfully
			}
		
		}
		return Status.FAILURE; //if the deletion failed
	}
	
	

}
