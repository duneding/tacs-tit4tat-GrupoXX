package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.service.ItemService;

@Controller
@RequestMapping(value = "/friends")
public class FriendsController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getFriends() {

		return "friends";
	}
	
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
	public ModelAndView getItemsFriends() {
//		List<Item> items = this.itemService.getItems();
		ModelAndView model = new ModelAndView("items");
		List<Item> items = new ArrayList<Item>();
		
		Usuario user1 = new Usuario("Franco");
		Usuario user2 = new Usuario("Fer");
		Usuario user3 = new Usuario("Martin");	
		Item item1 = new Item();
		item1.setId(1L);
		item1.setDescription("Ipod touch");
		item1.setOwner(user1);
		items.add(item1);
		
		Item item2 = new Item();
		item2.setId(2L);
		item2.setDescription("Galaxy S5");
		item2.setOwner(user2);
		items.add(item2);
		
		Item item3 = new Item();
		item3.setId(3L);
		item3.setDescription("Silla");
		item3.setOwner(user3);
		items.add(item3);
		
		model.setViewName("friends/items");
		model.addObject("items", items);
		return model;
	}
	
	
	

}
