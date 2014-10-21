package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.utn.tacs.tit4tat.model.Item;
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
		//TODO recibir una solicitud liviana que contenga solo idSolicitud y estado JSon
		this.solicitudService.changeStateOfSolicitud(permuteId, state);
		
		return "/notifications";
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	String getItemsSearch(@RequestParam(value = "id") String id, @RequestParam(value = "state") String state) {
		
		try {
//			this.solicitudService.changeStateOfSolicitud(id, state);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return "El parámetro es: " + id + " y " + state;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getNotifications() {
		ModelAndView model = new ModelAndView("notifications");
		
//		List<Solicitud> notifications = this.solicitudService.getSolicitudes();
		Item offeredItem = new Item();
		offeredItem.setDescription("Ipod Touch");
		
		Item requestItem = new Item();
		requestItem.setDescription("Galaxy S5");
		
		Solicitud sol = new Solicitud("Solicitud 1", requestItem, offeredItem);
		sol.setId(1L);
		
		List<Solicitud> notifications = new ArrayList<Solicitud>();
		notifications.add(sol);
		model.addObject("notifications", notifications);
		
		return model;
	}
}
