package com.mikaila.soap.webservices.eclipsesoapcoursemanagement;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs  //Enable web services
@Configuration  // spring configuration
public class WebServiceConfig {
	
	//Message dispatcher Servlet - to identify end points
	//Spring Application context
	//URL to expose web services /ws/
	//define a spring bean 
	//ServletRegistrationBean - map messageDispatcherServlet to the url

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true); 
		return new ServletRegistrationBean(messageDispatcherServlet,"/ws/*");
	}
	
	
	// Spring creates a wsdl for us by using the schema
	// /ws/courses.wsdl - where we expose our wsdl
	// PortType - CoursePort
	// Name space - http://mikaila.com/courses
	// course-details.xsd
	
	@Bean(name ="courses")  //because we want the name - courses.wsdl
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("CoursePort");  //port type is like an interface
		definition.setTargetNamespace("http://mikaila.com/courses");
		definition.setLocationUri("/ws");  //URI
		definition.setSchema(coursesSchema); //from below
		return definition;
	}
	//xsdSchema to create a courses schema
	@Bean
	public XsdSchema coursesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}
	
	
}



