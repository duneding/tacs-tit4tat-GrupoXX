package com.utn.tacs.tit4tat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemsController {

	@RequestMapping(value = "/items", method = RequestMethod.GET)
	public String items(ModelMap model) {
		
		model.addAttribute("recurso", new String("ITEMS"));
		return "items";
	}
	
	@RequestMapping(value = "/getItemsSearch", method = RequestMethod.GET)
	public @ResponseBody
	String getItemsSearch(@RequestParam(value = "name") String name){
		return "Nombre del item a buscar: " + name;
	}

}
