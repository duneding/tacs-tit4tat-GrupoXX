package com.utn.tacs.tit4tat.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.service.ItemService;

@Controller
@RequestMapping(value = "/items")
public class ItemsController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * Da de alta el item final
	 * @param item
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ModelAndView createItem(@RequestBody Item item) {
		  return null;  
	}
	
	/**
	 * Obtiene los items de un usuario
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getItems() {
//		List<Item> items = this.itemService.getItems();
		ModelAndView model = new ModelAndView("items");
		List<Item> items = new ArrayList<Item>();
		
		Item item1 = new Item();
		item1.setId(1L);
		item1.setDescription("Ipod touch");
		items.add(item1);
		
		Item item2 = new Item();
		item2.setId(2L);
		item2.setDescription("Galaxy S5");
		items.add(item2);
		
		Item item3 = new Item();
		item3.setId(3L);
		item3.setDescription("Silla");
		items.add(item3);
		
		model.setViewName("items");
		model.addObject("items", items);
		model.addObject("message", "Lista de items");
		return model;
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

	/**
	 * Recibe el Id de ML, llena un modelo Item y lo devuelve cargado para el formulario de crear item
	 * @param idItemMeli
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/items/create", method = RequestMethod.GET)
	public @ResponseBody String createFormItem(@RequestParam(value = "idItemMeli") String idItemMeli, Model model) {
//		MercadoLibre meli = new MercadoLibre();
//		JSONObject item  = meli.get
		Item item = new Item();
		
		try {
			item = new Item();
			item.setId(1L);
			item.setDescription("IPod 32GB");
			item.setPermalink(new URL("http://mercadolibre.com.ar/item/ml12312"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("item", item);
		
		return "/items/create";
	}
	
	
	@RequestMapping(value = "/{itemId}/share", method = RequestMethod.GET)
	public String shareMyCreationItem(@PathVariable("itemId") String itemid) {
		
		return "items";
	}
	
	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public String removeItem(@PathVariable("itemId") String itemId) {
		Item item = this.itemService.getItemsById(Long.parseLong(itemId));
		this.itemService.deleteItem(item);
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
