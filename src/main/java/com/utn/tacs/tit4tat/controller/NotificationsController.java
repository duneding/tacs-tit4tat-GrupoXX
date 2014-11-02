package com.utn.tacs.tit4tat.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
public class NotificationsController {

	@Autowired
	private SolicitudService solicitudService;

	List<Solicitud> notifications = new ArrayList<Solicitud>();
	
	/*@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public @ResponseBody
	String getItemsSearch(@RequestParam(value = "id") String id,
			@RequestParam(value = "state") String state) {

		try {
			this.solicitudService.changeStateOfSolicitud(id, state);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "La solicitud proceso correctamente: " + id;
	}*/
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getNotifications() {
		ModelAndView model = new ModelAndView("notifications");

		Item offeredItem = new Item();
		offeredItem.setDescription("Ipod Touch");

		Item requestItem = new Item();
		requestItem.setDescription("Galaxy S5");

		Solicitud sol = new Solicitud();
		sol.setId(1L);
		sol.setDetail("Solicitud 1");
		sol.setRequestItem(requestItem);
		sol.setOfferedItem(offeredItem);
		//
		// this.solicitudService.saveSolicitud(sol);
		//
		notifications.add(sol);
		// List<Solicitud> notifications =
		// this.solicitudService.getSolicitudesPendientes();
		model.setViewName("notifications/list");
		model.addObject("notifications", notifications);

		return model;
	}
		
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public  @ResponseBody String  createn(@RequestParam(value = "json") String jsonRequest) {

		//TODO> guardar en DB 
		return "redirect:create";
	}
	
	@SuppressWarnings("unchecked")
	@Consumes(value ="application/json")
	@RequestMapping (method = RequestMethod.POST)
	public @ResponseBody ModelAndView create(@RequestBody String jsonRequest) {	
		
		ModelAndView model = new ModelAndView("create");				
		JSONObject obj=new JSONObject();
		obj.put("create","OK");		 
		model.addObject("response", obj);
		
        return model;
    }	
	
	@SuppressWarnings("unchecked")
	@Consumes(value ="application/json")
	@RequestMapping (value="/reply", method = RequestMethod.PUT)
	public @ResponseBody ModelAndView update(@RequestBody String jsonRequest) {	
		
		ModelAndView model = new ModelAndView("edit");				
		JSONObject obj=new JSONObject();
		obj.put("update","OK");		 
		model.addObject("response", obj);
		
        return model;
    }	
	
	@RequestMapping(value="/create/{id}", method =RequestMethod.GET)
	//public  @ResponseBody ModelAndView  createGet(@RequestParam(value = "json") String jsonRequest) { 
	public  ModelAndView  createGet() { 
		Item item = new Item();
		Solicitud solicitud = new Solicitud();
		ModelAndView model = new ModelAndView("notifications/create");		
		try {
			solicitud.setId(1L);
			solicitud.setState(1);

		//model.addObject("solicitud", solicitud);		
		
		Solicitud notification = new Solicitud();
		notification.setId(1L);
		notification.setDetail("mydetails");
		
		Item itemOfrecido = new Item();
		itemOfrecido.setId(1L);
		itemOfrecido.setDescription("IPod 32GB");
		itemOfrecido.setPermalink(new URL("http://mercadolibre.com.ar/item/ml12312"));
		String[] category = {"Electronica"};
		itemOfrecido.setCategory(category);
		notification.setOfferedItem(itemOfrecido);
		
		
		Item itemSolicitado = new Item();
		itemSolicitado.setId(1L);
		itemSolicitado.setDescription("Bike");
		itemSolicitado.setPermalink(new URL("http://mercadolibre.com.ar/item/ml12312"));
		String[] category2 = {"NO SE"};
		item.setCategory(category2);

		
		notification.setRequestItem(itemSolicitado);
		model.addObject("notification", notification);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	

		
		//model.setViewName("notifications/create");
		return model;
	}
		
	@RequestMapping(value = "/edit/{notId}", method = RequestMethod.PUT)
	public String edit(@PathVariable("notId") String notId) {
		
		return "notifications/edit";
	}
	
	@RequestMapping(value = "/delete/{notId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("notId") String notId) {
		
		return "notifications/delete";
	}
	
	@RequestMapping(value = "/{notId}/accepted", method = RequestMethod.PUT)
	public String accepted(@PathVariable("notId") String notId) {
		
		return "notifications/accepted";
	}	
	
	@RequestMapping(value = "/{notId}/rejected", method = RequestMethod.PUT)
	public String rejected(@PathVariable("notId") String notId) {
		
		return "notifications/rejected";
	}		
	
	@SuppressWarnings("unchecked")
	@Consumes(value ="application/json")
	@RequestMapping (value = "/{notId}/share", method = RequestMethod.POST)
    //public @ResponseBody ModelAndView create(@ModelAttribute("item") Item item, BindingResult result) {
	public @ResponseBody ModelAndView share(@PathVariable("notId") String itemId, @RequestBody String jsonRequest) {	
		
		ModelAndView model = new ModelAndView("notifications/share");				
		JSONObject obj=new JSONObject();
		obj.put("share","OK");		 
		model.addObject("response", obj);
		
        return model;
    }	

}
