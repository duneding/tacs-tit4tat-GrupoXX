package com.utn.tacs.tit4tat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.service.SolicitudService;

@Controller
@RequestMapping("/notifications")
public class SolicitudesController {

	@Autowired
	private SolicitudService solicitudService;

	@RequestMapping(method = RequestMethod.POST)
	public String createPermute(Model model) {
		return "/notifications";
	}
	
	
	@RequestMapping(value = "/{permuteId}/{state}", method = RequestMethod.PUT)	
	public String responsePermuteNotification(@PathVariable("permuteId") String permuteId, @PathVariable("state") String state) {
		
		this.solicitudService.changeStateOfSolicitud(permuteId, state);
		
		return "/notifications";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getNotifications(ModelMap model) {

		List<Solicitud> notifications = this.solicitudService.getSolicitudes();
		model.addAttribute("notifications", notifications);
		return "/notifications";
	}
}
