package com.utn.tacs.tit4tat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utn.tacs.tit4tat.model.Item;
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
	
	@RequestMapping(value="/{userId}/items", method=RequestMethod.GET)
	public String getItemsByUser(@PathVariable("userId") String userId, Model model) {
		
		Long userIdLong = Long.getLong(userId);
		List<Item> items = this.itemService.getItemsByUser(userIdLong);
		model.addAttribute("items", items);
		
		return "friends/{userId}/items";
	}
	
	@RequestMapping(value="/items", method=RequestMethod.GET)
	public String items() {
				
		return "friends/items";
	}	
}
