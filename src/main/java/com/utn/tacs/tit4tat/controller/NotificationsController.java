package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.Blob;
import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.model.Solicitud;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.objectify.Utils;
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
	public ModelAndView getNotifications() {
		ModelAndView model = new ModelAndView("notifications");

		List<Solicitud> notifications = this.solicitudService
				.getSolicitudesPendientes();

		model.addObject("notifications", notifications);

		return model;
	}


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

	@SuppressWarnings("unchecked")
	@Consumes(value = "application/json")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(@RequestBody String request) {

		ModelAndView model = new ModelAndView("notifications");
		JSONObject obj = new JSONObject();
		obj.put("create", "OK");
		model.addObject("response", obj);

		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonRequest = (JSONObject) jsonParser.parse(request);
			Long owner_id = Long
					.valueOf(jsonRequest.get("owner_id").toString());
			Long item_id = Long.valueOf(jsonRequest.get("item_id").toString());
			Long user_id = Long.valueOf(jsonRequest.get("user_id").toString());
			Long user_item_id = Long.valueOf(jsonRequest.get("user_item_id")
					.toString());

			Item offeredItem = this.itemService.getItemsById(user_item_id);
			Item requestedItem = this.itemService.getItemsById(item_id);

			Usuario offeredUser = this.usuarioService.getUsuariosById(user_id);
			Usuario requestedUser = this.usuarioService
					.getUsuariosById(owner_id);

			Solicitud sol = new Solicitud();
			sol.setOfferedItem(offeredItem);
			sol.setOfferedUser(offeredUser);

			sol.setRequestItem(requestedItem);
			sol.setRequestUser(requestedUser);

			sol.setState(Solicitud.PENDING);

			this.solicitudService.saveSolicitud(sol);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return model;
	}

	@RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
	// public @ResponseBody ModelAndView createGet(@RequestParam(value = "json")
	// String jsonRequest) {
	public ModelAndView createGet() {
		Item item = new Item();
		Solicitud solicitud = new Solicitud();
		ModelAndView model = new ModelAndView("notifications/create");
		try {
			solicitud.setId(1L);
			solicitud.setState(1);

			// model.addObject("solicitud", solicitud);

			Solicitud notification = new Solicitud();
			notification.setId(1L);
			notification.setDetail("mydetails");

			Item itemOfrecido = new Item();
			itemOfrecido.setId(1L);
			itemOfrecido.setDescription("IPod 32GB");
			itemOfrecido
					.setPermalink("http://mercadolibre.com.ar/item/ml12312");
			String[] category = { "Electronica" };
			itemOfrecido.setCategory(category);
			notification.setOfferedItem(itemOfrecido);

			Item itemSolicitado = new Item();
			itemSolicitado.setId(1L);
			itemSolicitado.setDescription("Bike");
			itemSolicitado
					.setPermalink("http://mercadolibre.com.ar/item/ml12312");
			String[] category2 = { "NO SE" };
			item.setCategory(category2);

			notification.setRequestItem(itemSolicitado);
			model.addObject("notification", notification);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@SuppressWarnings("unchecked")
	@Consumes(value = "application/json")
	@RequestMapping(value = "/{notId}/share", method = RequestMethod.POST)
	// public @ResponseBody ModelAndView create(@ModelAttribute("item") Item
	// item, BindingResult result) {
	public @ResponseBody
	ModelAndView share(@PathVariable("notId") String itemId,
			@RequestBody String jsonRequest) {

		ModelAndView model = new ModelAndView("notifications/share");
		JSONObject obj = new JSONObject();
		obj.put("share", "OK");
		model.addObject("response", obj);

		return model;
	}
	
//	private void populateData() {
//		Usuario me = new Usuario("Martin");
//		Usuario other = new Usuario("Walter");
//		// Usuario user1 = new Usuario("Martin Dagostino");
//
//		this.usuarioService.saveUsuario(me);
//		this.usuarioService.saveUsuario(other);
//
//		Item offeredItem = new Item();
//		offeredItem.setDescription("Ipod Touch");
//		offeredItem.setOwner(me);
//		// offeredItem.setId(2l);
//
//		Item requestItem = new Item();
//		requestItem.setDescription("Galaxy S5");
//		requestItem.setOwner(other);
//		// requestItem.setId(1l);
//
//		this.itemService.saveItem(requestItem);
//		this.itemService.saveItem(offeredItem);
//
//		Solicitud sol = new Solicitud();
//		// sol.setId(99L);
//		sol.setDetail("Solicitud 1");
//		sol.setRequestItem(requestItem);
//		sol.setOfferedItem(offeredItem);
//
//		this.solicitudService.saveSolicitud(sol);
//
//		// notifications.add(sol);
//		// List<Solicitud> notifications =
//		// this.solicitudService.getSolicitudesPendientes();
//		// model.setViewName("notifications");
//	}
}
