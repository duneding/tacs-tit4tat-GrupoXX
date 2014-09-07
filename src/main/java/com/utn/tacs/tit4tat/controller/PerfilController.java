package com.utn.tacs.tit4tat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/perfil")
public class PerfilController {

	@RequestMapping(method = RequestMethod.GET)
	public String handlePerfil() {

		return "perfil";
	}
}
