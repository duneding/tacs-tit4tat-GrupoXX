package com.utn.tacs.tit4tat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

	@RequestMapping(method = RequestMethod.GET)
	public String items(ModelMap model) {
		
		model.addAttribute("recurso", new String("SOLICITUDES"));
		return "solicitudes";
	}
}
