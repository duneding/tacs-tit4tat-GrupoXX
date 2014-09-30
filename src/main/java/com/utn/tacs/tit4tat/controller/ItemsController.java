package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ItemsController {

/*	@RequestMapping(value = "/items", method = RequestMethod.GET)
	@ModelAttribute("result1")
	public ArrayList<String> items() {
		//model.addAttribute("result1", result1)
		//ItemServiceImpl service = new ItemServiceImpl();
		//List<Item> itemsList= service.getItems();
		//List<String> itemsList= this.getItems();
		ArrayList<String> list = new ArrayList<String>();
		list.add("List A");
		list.add("List B");
		list.add("List C");
		list.add("List D");
		list.add("List 1");
		list.add("List 2");
		list.add("List 3");
		//model.addAttribute("result1", list);
		//ModelAndView modelAndView1 = new ModelAndView("items");
		//modelAndView1.addObject("lists",itemsList);
 
		return list;
	}*/
	
	@RequestMapping(value = "/items", method = RequestMethod.GET)
	public ModelAndView getdata() {
		String message = "WELCOME SPRING MVC";  
        return new ModelAndView("items", "message", message);  
	}
 

	@RequestMapping(value = "/getItemsSearch", method = RequestMethod.GET)
	public @ResponseBody
	String getItemsSearch(@RequestParam(value = "name") String name) {
		return "Nombre del item a buscar: " + name;
	}
	
	/*@RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getItem(@PathVariable("id") String id ) {
		//serviceItem.GetById(id);
		return "item";
	}*/
	@RequestMapping(value = "/item", method = RequestMethod.GET)
	public ModelAndView getItem() {
		String message = "WELCOME SPRING MVC";  
        return new ModelAndView("item", "message", message);  
	}
	
	@RequestMapping(value = "/item", method = RequestMethod.POST)
	public String SaveItem(ModelMap model) {
		return "item";
	}
	
	private List<String> getItems(){
		List<String> list = new ArrayList<String>();
		list.add("List A");
		list.add("List B");
		list.add("List C");
		list.add("List D");
		list.add("List 1");
		list.add("List 2");
		list.add("List 3");
 
		return list;
		
	}

}
