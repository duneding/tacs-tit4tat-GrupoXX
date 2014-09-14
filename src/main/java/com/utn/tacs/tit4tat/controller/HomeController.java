package com.utn.tacs.tit4tat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String index(ModelMap model) {
		return "home";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() {

		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String signed_request(ModelMap model) {
		return "home";
	}
}