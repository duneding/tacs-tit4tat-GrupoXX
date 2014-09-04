package com.utn.tacs.tit4tat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
 	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index( ModelMap model ) {
		return "index";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home( ModelMap model ) {
		return "index";
	}
}