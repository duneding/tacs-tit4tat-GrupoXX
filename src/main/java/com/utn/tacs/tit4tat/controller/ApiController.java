package com.utn.tacs.tit4tat.controller;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/api")
public class ApiController {

	@SuppressWarnings("unchecked")
	@Consumes(value ="application/json")
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView login(@RequestBody String request, HttpSession session) {
		
		//TODO loggear con datos de usuario, si no existe crear uno
		ModelAndView model = new ModelAndView("redirect:/login");			
		model.addObject("request", request);		
		model.addObject("session", session);
		//RedirectView redirectView = new RedirectView("/home/login", true);
		//redirectView.addStaticAttribute("request", request);
		
		return model;
	}
}
