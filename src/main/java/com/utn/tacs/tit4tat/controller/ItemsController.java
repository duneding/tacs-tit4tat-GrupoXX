package com.utn.tacs.tit4tat.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.utn.tacs.tit4tat.meli.MercadoLibre;
import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.service.ItemService;

@Controller
@RequestMapping(value = "/items")
public class ItemsController {
	
	@Autowired
	private ItemService itemService;
	
	List<Item> items = new ArrayList<Item>();
	
	/**
	 * Da de alta el item final
	 * @param item
	 * @return
	 */
	@RequestMapping (value = "/list", method = RequestMethod.POST)
    public String addContact(@ModelAttribute("item")
    Item item, BindingResult result) {
		//Insertar Item
		items.add(item);
        return "redirect:/items/list";
    }
	/**
	 * Elimina Item
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/{itemId}", method = RequestMethod.DELETE)
	public @ResponseBody String removeItem(@PathVariable("itemId") String itemId) {
		return "El Item fue eliminado correctamente";
	}
	
	/**
	 * Obtiene los items de un usuario
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getItems() {
//		List<Item> items = this.itemService.getItems();
		ModelAndView model = new ModelAndView("items");
				
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
		
		model.setViewName("items/list");
		model.addObject("items", items);
		return model;
	}
	
	/**
	 * Busca en ML todos los items "name"
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/getItemsSearch", method = RequestMethod.GET)
	public @ResponseBody
	List<Item> getItemsSearch(@RequestParam(value = "name") String name) {
		MercadoLibre meli = MercadoLibre.getInstance();
		List<Item> items = meli.searchlListItems(name);
		
		/*List<Item> items = new ArrayList<Item>();
		String[] categoria = {"Celulares"};
		
		Item item1 = new Item();
		item1.setId(1L);
		item1.setDescription("Ipod touch");
		item1.setCategory(categoria);
		items.add(item1);
		
		Item item2 = new Item();
		item2.setId(2L);
		item2.setDescription("Galaxy S5");
		item2.setCategory(categoria);
		items.add(item2);
		
		Item item3 = new Item();
		item3.setId(3L);
		item3.setDescription("Silla");
		item3.setCategory(categoria);
		items.add(item3);*/
		return items;
	}

	
	/**
	 * Recibe el Id de ML, llena un modelo Item y lo devuelve cargado para el formulario de crear item
	 * @param idItemMeli
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/create/{idItemMeli}", method = RequestMethod.GET)
	public ModelAndView createFormItem(@PathVariable("idItemMeli") String idItemMeli) {
		Item item = new Item();
		try {
			String[] categoria = {idItemMeli};
			item = new Item();
			item.setId(1L);
			item.setDescription("IPod 32GB");
			item.setPermalink(new URL("http://mercadolibre.com.ar/item/ml12312"));
			item.setCategory(categoria);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ModelAndView model = new ModelAndView("items/create");
		model.addObject("item", item);
		return model;
	}

	
	@RequestMapping(value = "/{itemId}/share", method = RequestMethod.GET)
	public String shareMyCreationItem(@PathVariable("itemId") String itemid) {
		
		return "items/share";
	}
		
	@RequestMapping(value = "/edit/{itemId}", method = RequestMethod.PUT)
	public String edit(@PathVariable("itemId") String itemid) {
		
		return "items/edit";
	}
	
	@RequestMapping(value = "/delete/{itemId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("itemId") String itemid) {
		
		return "items/delete";
	}		
	
	@RequestMapping(value = "share/{itemId}", method = RequestMethod.PUT)
	public String share(@PathVariable("itemId") String itemid) {
		
		return "items/share";
	}
		
	/*@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public String removeItem(@PathVariable("itemId") String itemId) {
		Item item = this.itemService.getItemsById(Long.parseLong(itemId));
		this.itemService.deleteItem(item);
		return "items";
	}
	
	@RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getItem(@PathVariable("id") String id ) {
		//serviceItem.GetById(id);
		return "item";
	}*/
	
//	@RequestMapping(value = "/item", method = RequestMethod.POST)
//	public String SaveItem(ModelMap model) {
//		return "item";
//	}
	
}
