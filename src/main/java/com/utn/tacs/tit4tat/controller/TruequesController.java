package com.utn.tacs.tit4tat.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/trueques")
public class TruequesController {
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String items() {
				
		return "trueques/create";
	}	
}