package com.utn.tacs.tit4tat.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.management.Notification;

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
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.service.SolicitudService;

@Controller
@RequestMapping("/notifications")
public class NotificationsController {

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
		
		return "La solicitud proceso correctamente: " + id;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getNotifications() {
		ModelAndView model = new ModelAndView("notifications");
		
		Item offeredItem = new Item();
		offeredItem.setDescription("Ipod Touch");
		
		Item requestItem = new Item();
		requestItem.setDescription("Galaxy S5");
		
		Solicitud sol = new Solicitud("Solicitud 1", requestItem, offeredItem);
		sol.setId(1L);
//		
//		this.solicitudService.saveSolicitud(sol);
//		
		List<Solicitud> notifications = new ArrayList<Solicitud>();
		notifications.add(sol);
//		List<Solicitud> notifications = this.solicitudService.getSolicitudesPendientes();
		model.setViewName("notifications/list");
		model.addObject("notifications", notifications);
		
		return model;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.PUT)
	public @ResponseBody
	String list() {
		
		return "notifications/list";
	}
	
	/**
	 * Recibe el Id de ML, llena un modelo Item y lo devuelve cargado para el formulario de crear item
	 * @param idItemMeli
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create/{idItem}", method = RequestMethod.GET)
	public ModelAndView CreateNotification(@PathVariable("idItem") String idItem) {
		Item item1 = new Item();
		Item item2 = new Item();
			String[] categoria = {idItem};
			item1 = new Item();
			item1.setId(1L);
			item1.setDescription("IPod 32GB");
			try {
				item1.setPermalink(new URL("http://mercadolibre.com.ar/item/ml12312"));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			item1.setCategory(categoria);
			Usuario user = new Usuario("Martin");
			item1.setOwner(user);
			
			item2 = new Item();
			item2.setId(1L);
			item2.setDescription("IPod 32GB");
			try {
				item2.setPermalink(new URL("http://mercadolibre.com.ar/item/ml12312"));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			item2.setCategory(categoria);
			Usuario user2 = new Usuario("Franco");
			item2.setOwner(user2);
			
			Solicitud solicitud = new Solicitud("", item1, item2);

		ModelAndView model = new ModelAndView("notifications/create");
		model.addObject("solicitud", solicitud);
		return model;
	}
	

}
