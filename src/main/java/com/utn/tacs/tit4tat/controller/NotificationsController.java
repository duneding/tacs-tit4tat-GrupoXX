package com.utn.tacs.tit4tat.controller;

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
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.service.ItemService;
import com.utn.tacs.tit4tat.service.SolicitudService;
import com.utn.tacs.tit4tat.service.UsuarioService;

@Controller
@RequestMapping("/notifications")
public class NotificationsController {

	@Autowired
	private SolicitudService solicitudService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ItemService itemService;

	List<Solicitud> notifications = new ArrayList<Solicitud>();

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	List<Solicitud> getNotifications(
			@RequestParam(value = "userId") String userId) {

		// TODO Falta filtrar por usuario
		List<Solicitud> notifications = this.solicitudService
				.getSolicitudesPendientes();
		// List<Solicitud> notifications =
		// this.solicitudService.getSolicitudesByUsuario(userId);

		return notifications;
	}

	// @RequestMapping(method = RequestMethod.GET)
	// public ModelAndView getNotifications() {
	// ModelAndView model = new ModelAndView("notifications");
	//
	// List<Solicitud> notifications =
	// this.solicitudService.getSolicitudesPendientes();
	//
	// model.addObject("notifications", notifications);
	//
	// return model;
	// }

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	String getItemsSearch(@RequestParam(value = "id") String id,
			@RequestParam(value = "state") String state) {

		try {
			this.solicitudService.changeStateOfSolicitud(id, state);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "Notificacion ID: " + id + " resuelta.";
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	String create(@RequestParam(value = "owner_id") String owner_id,
			@RequestParam(value = "item_id") String item_id,
			@RequestParam(value = "user_id") String user_id,
			@RequestParam(value = "user_item_id") String user_item_id) {

		Item offeredItem = this.itemService.getItemsById(Long.parseLong(user_item_id));
		Item requestedItem = this.itemService.getItemsById(Long.parseLong(item_id));

		Usuario offeredUser = this.usuarioService.getUsuariosById(Long.parseLong(user_id));
		Usuario requestedUser = this.usuarioService.getUsuariosById(Long.parseLong(owner_id));

		Solicitud sol = new Solicitud();
		sol.setOfferedItem(offeredItem);
		sol.setOfferedUser(offeredUser);

		sol.setRequestItem(requestedItem);
		sol.setRequestUser(requestedUser);

		sol.setState(Solicitud.PENDING);

		this.solicitudService.saveSolicitud(sol);

		return "Solicitud de trueque creado correctamente.";
	}

	@SuppressWarnings("unchecked")
	@Consumes(value = "application/json")
	@RequestMapping(value = "/{notId}/share", method = RequestMethod.POST)
	public @ResponseBody
	ModelAndView share(@PathVariable("notId") String itemId,
			@RequestBody String jsonRequest) {

		ModelAndView model = new ModelAndView("notifications/share");
		JSONObject obj = new JSONObject();
		obj.put("share", "OK");
		model.addObject("response", obj);

		return model;
	}
}
