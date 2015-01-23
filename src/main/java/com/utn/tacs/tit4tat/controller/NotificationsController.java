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

	@Consumes(value = "application/json")
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	String getItemsSearch(@RequestBody String request) {
	/*String getItemsSearch(@RequestParam(value = "id") String id,
			@RequestParam(value = "state") String state) {*/
		JSONObject jsonRequest = new JSONObject();		
		JSONParser jsonParser = new JSONParser();
		String id = "";
		String state = "";
		
		try {			
			jsonRequest = (JSONObject) jsonParser.parse(request);
			
			if (jsonRequest.get("id")!=null){
				id = jsonRequest.get("id").toString();				
			}else{				
				return "Error falta ID";
			}
			
			if (jsonRequest.get("state")!=null){
				state = jsonRequest.get("state").toString();				
			}else{				
				return "Error falta STATE";
			}
			
			this.solicitudService.changeStateOfSolicitud(id, state);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "Notificacion ID: " + id + " resuelta.";
	}

	@Consumes(value = "application/json")
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
			String message = jsonRequest.get("message").toString();

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
			sol.setMessage(message);

			sol.setState(Solicitud.PENDING);

			sol = this.solicitudService.saveSolicitud(sol);


		//return "Solicitud de trueque " + sol.getId() + " creado correctamente";
			return sol.getId().toString();
	}
	
	@RequestMapping(value="/count", method = RequestMethod.GET)
	public @ResponseBody int getNotificationsCount(@RequestParam(value = "userId") String userId) {
		int size = 0;
		
		try {
			Usuario usuario = this.usuarioService.getUsuariosById(Long.parseLong(userId));
			List<Solicitud> notifications = this.solicitudService.getSolicitudesPendientesByUser(usuario);
			size = notifications.size();
		} catch (Exception e) {
			size = 0;
		}
		
		return size;
	}
	
	@RequestMapping(value = "{idNotifications}/share", method = RequestMethod.POST)
	//public String share(@PathVariable("itemId") String itemid) {
	public @ResponseBody String share(@PathVariable("idNotifications") String idNotifications) {
		
		//return "items/share";
		return "La solicitud " + idNotifications + " debe compartirse en un ambiente dentro de Facebook";
	}
}
