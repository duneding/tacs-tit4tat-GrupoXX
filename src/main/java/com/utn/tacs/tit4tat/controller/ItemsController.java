package com.utn.tacs.tit4tat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/items")
public class ItemsController {

	@RequestMapping(method = RequestMethod.GET)
	public String items(ModelMap model) {
		
		model.addAttribute("recurso", new String("ITEMS"));
		return "items";
	}
}
