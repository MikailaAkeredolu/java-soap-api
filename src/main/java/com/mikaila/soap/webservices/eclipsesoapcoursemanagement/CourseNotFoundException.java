package com.mikaila.soap.webservices.eclipsesoapcoursemanagement;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

//declare client fault using annotation
@SoapFault(faultCode=FaultCode.CLIENT)
public class CourseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;  //add serialization id
	
	public CourseNotFoundException(String message) {  //basic super constructor extend the string version
		super(message);
	}


}
