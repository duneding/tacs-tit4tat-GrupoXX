package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.service.ItemService;
import com.utn.tacs.tit4tat.service.UsuarioService;

@Controller
@RequestMapping(value = "/friends")
public class FriendsController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private UsuarioService usuarioService;
	
//	@RequestMapping(value="/notify", method =RequestMethod.GET)
	//public  @ResponseBody ModelAndView  createNotification(@RequestParam(value = "json") String jsonRequest) {
//	public  String notifyFriend() {
//		
//		/*ModelAndView model = new ModelAndView("/notifications/create");
//		model.addObject("solicitud", jsonRequest);*/
////		String projectUrl = "yahoo.com";
//		return "redirect:/notifications/create/"+1;
//	}
	
	/**
	 * Obtiene los items de mis amigos
	 * @return
	 */
	@RequestMapping(value = "/items", method = RequestMethod.GET)
	public @ResponseBody
	List<Item> getItemsFriends(@RequestParam(value = "idFriends") String idFriends) {
		List<Item> items = new ArrayList<Item>();
//		String[] friends = { idFriends.toString() };
		String[] friends = idFriends.split(",");

		for (String friendId : friends) {
			Usuario usuario = this.usuarioService.getUsuariosById(Long.parseLong(friendId));
			
			if (usuario != null)
				items.addAll(this.itemService.getItemsByUser(usuario));
		}

		return items;
	}
}
