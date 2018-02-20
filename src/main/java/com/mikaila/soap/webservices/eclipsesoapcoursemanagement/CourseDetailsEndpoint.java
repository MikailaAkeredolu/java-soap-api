package com.mikaila.soap.webservices.eclipsesoapcoursemanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.mikaila.courses.CourseDetails;
import com.mikaila.courses.DeleteCourseDetailsRequest;
import com.mikaila.courses.DeleteCourseDetailsResponse;
import com.mikaila.courses.GetAllCourseDetailsRequest;
import com.mikaila.courses.GetAllCourseDetailsResponse;
import com.mikaila.courses.GetCourseDetailsRequest;
import com.mikaila.courses.GetCourseDetailsResponse;
import com.mikaila.soap.webservices.eclipsesoapcoursemanagement.CourseDetailsService.Status;


//@RequestPayload to annotate the - input what will come to us in the request
//@ResponsePayload - the output/response needs to be converted back to xml
//@PayloadRoot - Accept this request for the name space specified and the request in the xsd file

//Mark this as an end point
@Endpoint 
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService service;
	
	 	
		@PayloadRoot(namespace="http://mikaila.com/courses", localPart="GetCourseDetailsRequest") 
		@ResponsePayload 
		public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {  //convert from Request.xml to java
			
			Course course = service.findById(request.getId()); 	//use the service
			if(course == null) {
				//Throw a custom exception
				throw new CourseNotFoundException("Invalid Course Id " + request.getId());  
			}
			return mapCourseDetails(course);
		}
		
		
		
		@PayloadRoot(namespace="http://mikaila.com/courses", localPart="GetAllCourseDetailsRequest") 
		@ResponsePayload 
		public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {  //convert from Request.xml to java
			//use the service
			 List<Course>courses = service.findAll();
			return mapAllCourseDetails(courses);
		}
		
		
		@PayloadRoot(namespace="http://mikaila.com/courses", localPart="DeleteCourseDetailsRequest") 
		@ResponsePayload 
		public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {  //convert from Request.xml to java
			Status status = service.deleteById(request.getId());  //Status from CourseDetails service enum
			 DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();  //create an instance
			 response.setStatus(mapStatus(status)); //map the two Status's - setStatus comes from xsd Java Object
			return response;
		}



		//METHODS
		
		//Method to Get a single Course Response
		private GetCourseDetailsResponse mapCourseDetails(Course course) {
			GetCourseDetailsResponse response = new GetCourseDetailsResponse();
			response.setCourseDetails(mapCourse(course));
			return response;
		}

		
		//Method to Get all course details response
		private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
			GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
			for(Course course: courses) {
				CourseDetails mapCourse = mapCourse(course);  //map each course 
				response.getCourseDetails().add(mapCourse);  //add it to the list of courseDetails
			}
			return response;
		}
		
		//Method to create a single Course
		private CourseDetails mapCourse(Course course) {
			CourseDetails courseDetails = new CourseDetails();
			courseDetails.setId(course.getId());
			courseDetails.setName(course.getName());
			courseDetails.setDescription(course.getDescription());
			return courseDetails;
		}
		
		//Method for Status enum

	private com.mikaila.courses.Status mapStatus(Status status) {  //parameter is service status
		if(status == Status.FAILURE) {
			return com.mikaila.courses.Status.FAILURE; //mapping the service Status to the Status we defined in the xsd bean
		}
		return com.mikaila.courses.Status.SUCCESS;
	}
	
	
	
		
}
