package com.utn;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

@Controller
public class Tit4TatController {
 	//
 	// Create a file, index.jsp within folder war/WEB-INF/views. 
 	// This index.jsp would showup at 127.0.0.1:8888.
 	//
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index( ModelMap model ) {
		return new ModelAndView("index");
	}
	//
	// Create a file, java_functional_interface.jsp within folder war/WEB-INF/views. 
	// This file would be accessed when the URL accessed from website is   http://127.0.0.1:8888/java_functional_interface.html
 	//
	@RequestMapping(value = "/java_functional_interface.html", method = RequestMethod.GET)
	public ModelAndView getJavaFuncInterface( ModelMap model ) {
		return new ModelAndView("java_functional_interface");
	}
	//
	// Create a file, aboutus.jsp within folder war/WEB-INF/views. 
	// This file would be accessed when the URL accessed from website is http://127.0.0.1:8888/aboutus.html
 	//	
	@RequestMapping(value = "/aboutus.html", method = RequestMethod.GET)
	public ModelAndView getAboutUs( ModelMap model ) {
		return new ModelAndView("aboutus");
	}
	//
	// Create a file, hello.jsp within folder war/WEB-INF/views. 
	// This file would be accessed when the URL accessed from website is http://127.0.0.1:8888/springmvc/helloworld
 	//		
	@RequestMapping(value = "/springmvc/helloworld", method = RequestMethod.GET)
	public ModelAndView newUser( ModelMap model ) {
		return new ModelAndView("hello");
	} 
}