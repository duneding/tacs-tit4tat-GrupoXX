package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.service.ItemService;

@Controller
@RequestMapping(value = "/friends")
public class FriendsController {

	@Autowired
	private ItemService itemService;
	
	/*@RequestMapping(value="/{userId}/items", method=RequestMethod.GET)
	public String getItemsByUser(@PathVariable("userId") String userId, Model model) {
		
		Long userIdLong = Long.getLong(userId);
		List<Item> items = this.itemService.getItemsByUser(userIdLong);
		model.addAttribute("items", items);
		
		return "friends/{userId}/items";
	}*/
	
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
	public String getItemsFriends() {
/*		ModelAndView model = new ModelAndView("friends/items");

		List<Item> items = this.itemService.getItems();
		
		model.addObject("items", items);
		//items.get(0).getOwner()
*/		
		return "friends/items";
	}
	
	
	@RequestMapping(value = "/ItemsJson/{idFriends}", method = RequestMethod.GET)
	public @ResponseBody
	List<Item>  getItemsJsonFriends(@PathVariable("idFriends") String idFriends) {

		List<Item> items = this.itemService.getItems();
		
		
		return items;
	}

	
}
