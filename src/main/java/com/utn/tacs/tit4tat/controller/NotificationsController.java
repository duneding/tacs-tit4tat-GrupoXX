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
	public @ResponseBody List<Solicitud> getNotifications(@RequestParam(value = "userId") String userId) {

		Usuario usuario = this.usuarioService.getUsuariosById(Long.parseLong(userId));
		List<Solicitud> notifications = this.solicitudService.getSolicitudesByUser(usuario);

		return notifications;
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

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String create(@RequestBody String request) {
		JSONParser jsonParser;
		JSONObject jsonRequest;
		
		try {
			jsonParser = new JSONParser();
			jsonRequest = (JSONObject) jsonParser.parse(request);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
			Long owner_id = Long
					.parseLong(jsonRequest.get("owner_id").toString());
			Long item_id = Long.parseLong(jsonRequest.get("item_id").toString());
			Long user_id = Long.parseLong(jsonRequest.get("user_id").toString());
			Long user_item_id = Long.parseLong(jsonRequest.get("user_item_id").toString());

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


		return "Solicitud de trueque creado correctamente";
	}
}
