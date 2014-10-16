package com.utn.tacs.tit4tat.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.utn.tacs.tit4tat.meli.MercadoLibre;
import com.utn.tacs.tit4tat.model.Item;

@Controller
@RequestMapping(value = "/items")
public class ItemsController {
	
	/**
	 * Da de alta el item final
	 * @param item
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ModelAndView createItem(@RequestBody Item item) {
		  return null;
//        return new ModelAndView("items", "message", message);  
	}
	
	/**
	 * Obtiene los items de un usuario
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getItems() {
		String message = "WELCOME SPRING MVC";  
		return new ModelAndView("items", "message", message);  
	}
	
	/**
	 * Busca en ML todos los items "name"
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/getItemsSearch", method = RequestMethod.GET)
	public @ResponseBody
	String getItemsSearch(@RequestParam(value = "name") String name) {
//		MercadoLibre meli = new MercadoLibre();
//		JSONObject response  = meli.searchJSONItems(name);
//		return response.toString();
		return "El parámetro es: " + name;
	}
	
	
	@RequestMapping(value = "/{itemId}/share", method = RequestMethod.GET)
	public String shareMyCreationItem(@PathVariable("itemId") String itemid) {
		
		return "items";
	}
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public String removeItem(@PathVariable("itemId") String itemId) {
		return "items";
	}
	
	/*@RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getItem(@PathVariable("id") String id ) {
		//serviceItem.GetById(id);
		return "item";
	}*/
	
//	@RequestMapping(value = "/item", method = RequestMethod.POST)
//	public String SaveItem(ModelMap model) {
//		return "item";
//	}
	
//	private List<String> getItems(){
//		List<String> list = new ArrayList<String>();
//		list.add("List A");
//		list.add("List B");
//		list.add("List C");
//		list.add("List D");
//		list.add("List 1");
//		list.add("List 2");
//		list.add("List 3");
// 
//		return list;
//		
//	}

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
}
