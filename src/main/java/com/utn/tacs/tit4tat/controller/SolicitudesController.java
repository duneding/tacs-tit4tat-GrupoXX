package com.utn.tacs.tit4tat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.service.SolicitudService;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

	@Autowired
	private SolicitudService solicitudService;

	@RequestMapping(method = RequestMethod.GET)
	public String items(ModelMap model) {

		this.solicitudService.saveSolicitud(new Solicitud("Solicitud de Trueque1"));
		this.solicitudService.saveSolicitud(new Solicitud("Solicitud de Trueque2"));
		
		for (Solicitud solicitud : this.solicitudService.getSolicitudes()) {
			System.out.println(solicitud);
		}

		model.addAttribute("recurso", new String("SOLICITUDES"));
		return "solicitudes";
	}
}
