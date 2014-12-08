package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.Blob;
import com.utn.tacs.tit4tat.meli.MercadoLibre;
import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.model.ItemMeli;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.service.ItemService;
import com.utn.tacs.tit4tat.service.SolicitudService;
import com.utn.tacs.tit4tat.service.UsuarioService;

@Controller
@RequestMapping(value = "/items")
public class ItemsController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private SolicitudService solicitudService;

	/**
	 * Elimina Item
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	public @ResponseBody
	String removeItem(@PathVariable("itemId") String itemId) {
		Item item = this.itemService.getItemsById(Long.valueOf(itemId));
		this.itemService.deleteItem(item);
		// TODO Se debe que eliminar las solicitudes que contengan el item
		// this.solicitudService.deleteSolicitudWithItem(item);
		return "El Item fue eliminado correctamente";
	}

	/**
	 * Busca en ML todos los items "name"
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/getItemsSearch", method = RequestMethod.GET)
	public @ResponseBody
	List<ItemMeli> getItemsSearch(@RequestParam(value = "name") String name) {
		MercadoLibre meli = MercadoLibre.getInstance();
		List<ItemMeli> items = meli.searchlListItems(name);

		/*
		 * List<ItemMeli> items = new ArrayList<ItemMeli>(); String[] categoria
		 * = {"Celulares"}; ItemMeli item1 = new ItemMeli(); item1.setId(1L);
		 * item1.setDescription("Ipod touch"); item1.setCategory(categoria);
		 * items.add(item1);
		 * 
		 * ItemMeli item2 = new ItemMeli(); item2.setId(2L);
		 * item2.setDescription("Galaxy S5"); item2.setCategory(categoria);
		 * items.add(item2);
		 * 
		 * ItemMeli item3 = new ItemMeli(); item3.setId(3L);
		 * item3.setDescription("Silla"); item3.setCategory(categoria);
		 * items.add(item3);
		 */
		return items;
	}

//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public ModelAndView getCreate(HttpSession session) {
//
//		ModelAndView model = new ModelAndView("items/create");
//		/*
//		 * Item item = new Item();
//		 * item.setId(Long.valueOf(session.getAttribute("id").toString()));
//		 * item.setDescription(session.getAttribute("description").toString());
//		 * item
//		 * .setShortDescription(session.getAttribute("shortDescription").toString
//		 * ()); item.setCategory((String[])session.getAttribute("category"));
//		 * item.setImage((Blob)session.getAttribute("image"));
//		 * item.setPermalink(session.getAttribute("permalink").toString());;
//		 * item.setOwner((Usuario) session.getAttribute("user").toString());
//		 */
//
//		model.addObject("item", session.getAttribute("item"));
//		model.addObject("category", session.getAttribute("category").toString());
//		model.addObject("image", session.getAttribute("image").toString());
//		// model.addObject("category", session.getAttribute("category"));
//		return model;
//	}

	@SuppressWarnings("unchecked")
	@Consumes(value = "application/json")
	@RequestMapping(value = "/{itemId}/share", method = RequestMethod.POST)
	// public @ResponseBody ModelAndView create(@ModelAttribute("item") Item
	// item, BindingResult result) {
	public @ResponseBody
	ModelAndView share(@PathVariable("itemId") String itemId,
			@RequestBody String jsonRequest) {

		ModelAndView model = new ModelAndView("items/share");
		JSONObject obj = new JSONObject();
		obj.put("share", "OK");
		model.addObject("response", obj);

		return model;
	}

	@SuppressWarnings("unchecked")
	@Consumes(value = "application/json")
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody
	ModelAndView edit(@RequestBody String jsonRequest) {

		ModelAndView model = new ModelAndView("items");
		JSONObject obj = new JSONObject();
		obj.put("update", "OK");
		model.addObject("response", obj);

		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	String createItem(	@RequestParam(value = "id") String id,
						@RequestParam(value = "short_description") String shortDescription,
						@RequestParam(value = "description") String description,
						@RequestParam(value = "image") String image,
						@RequestParam(value = "permalink") String permalink,
						@RequestParam(value = "owner") String ownerId,
						@RequestParam(value = "category") String category,
						@RequestParam(value = "thumbnail") String thumbnail) {

		Item newItem = new Item();
		newItem.setId(Long.parseLong(id));
		newItem.setShortDescription(shortDescription);
		newItem.setDescription(description);
		//newItem.setImage(Utils.getImageAsBlob(image));
		Blob imageToBlob = new Blob(image.getBytes());
		newItem.setImage(imageToBlob);
		
		newItem.setPermalink(permalink);
		newItem.setThumbnail(thumbnail);
		
		String[] arrCategory = {category};
		newItem.setCategory(arrCategory);
		
		Usuario owner = this.usuarioService.getUsuariosById(Long.parseLong(ownerId));
		
		newItem.setOwner(owner);
		
		this.itemService.saveItem(newItem);

		return "Item creado correctamente. Id = " + id;
	}
	
	/**
	 * Obtiene los items de un usuario
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	List<Item> listItems(@RequestParam(value = "userId") String userId) {
		List<Item> items = new ArrayList<Item>();
		Usuario usuario = this.usuarioService.getUsuariosById(Long.parseLong(userId));

		for (Item item : this.itemService.getItemsByUser(usuario)) {
			items.add(item);
		}

		return items;
	}
	
//	@SuppressWarnings("unchecked")
//	@Consumes(value = "application/json")
//	@RequestMapping(method = RequestMethod.POST)
//	public ModelAndView create(@RequestBody String request) {
//
//		ModelAndView model = new ModelAndView("items");
//		JSONObject obj = new JSONObject();
//		obj.put("create", "OK");
//		model.addObject("response", obj);
//
//		try {
//			JSONParser jsonParser = new JSONParser();
//			JSONObject jsonRequest = (JSONObject) jsonParser.parse(request);
//			String description = jsonRequest.get("description").toString();
//			String shortDescription = jsonRequest.get("short_description")
//					.toString();
//			String permalink = jsonRequest.get("permalink").toString();
//			Long id = Long.valueOf(jsonRequest.get("id").toString());
//			Usuario owner = Utils.generateUser(jsonRequest.get("owner")
//					.toString());
//			// Blob image = (Blob)jsonRequest.get("image");
//			String[] category = { jsonRequest.get("category").toString() };
//			Blob image = Utils.getImageAsBlob(jsonRequest.get("image")
//					.toString());
//
//			Item item = new Item();
//			item.setDescription(description);
//			item.setShortDescription(shortDescription);
//			item.setOwner(owner);
//			item.setId(id);
//			item.setPermalink(permalink);
//			item.setImage(image);
//			item.setCategory(category);
//			this.itemService.saveItem(item);
//
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
//
//		return model;
//	}

	@RequestMapping(value = "/delete/{itemId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("itemId") String itemid) {

		return "items/delete";
	}

	@RequestMapping(value = "share/{itemId}", method = RequestMethod.PUT)
	public String share(@PathVariable("itemId") String itemid) {

		return "items/share";
	}

	/*
	 * @RequestMapping(value = "/{itemId}", method = RequestMethod.DELETE)
	 * public String removeItem(@PathVariable("itemId") String itemId) { Item
	 * item = this.itemService.getItemsById(Long.parseLong(itemId));
	 * this.itemService.deleteItem(item); return "items"; }
	 * 
	 * @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public String getItem(@PathVariable("id") String id ) {
	 * //serviceItem.GetById(id); return "item"; }
	 */

	// @RequestMapping(value = "/item", method = RequestMethod.POST)
	// public String SaveItem(ModelMap model) {
	// return "item";
	// }
}
