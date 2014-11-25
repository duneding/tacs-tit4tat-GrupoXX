package com.utn.tacs.tit4tat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.service.ItemService;

@Controller
@RequestMapping(value = "/friends")
public class FriendsController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/notify", method =RequestMethod.GET)
	//public  @ResponseBody ModelAndView  createNotification(@RequestParam(value = "json") String jsonRequest) {
	public  String notifyFriend() {
		
		/*ModelAndView model = new ModelAndView("/notifications/create");
		model.addObject("solicitud", jsonRequest);*/
		String projectUrl = "yahoo.com";
		return "redirect:/notifications/create/"+1;
	}
	
	/**
	 * Obtiene los items de mis amigos
	 * @return
	 */
	@RequestMapping(value = "/items", method = RequestMethod.GET)
	public @ResponseBody
	List<Item> getItemsFriends(@RequestParam(value = "idFriends") String idFriends) {
		
		//TODO discriminar por ids de amigos
		List<Item> items = this.itemService.getItems();	
		return items;
	}
	
	
//	@RequestMapping(value = "/ItemsJson/{idFriends}", method = RequestMethod.GET)
//	public @ResponseBody
//	List<Item>  getItemsJsonFriends(@PathVariable("idFriends") String idFriends) {
//
//		List<Item> items = this.itemService.getItems();
//		
//		
//		return items;
//	}
}
