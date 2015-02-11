package com.utn.tacs.tit4tat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.utn.tacs.tit4tat.objectify.OfyService;
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
	ModelAndView edit(@RequestBody String request) {

		//OfyService.ofy().delete().keys(OfyService.ofy().load().type(Item.class).keys());
		//OfyService.ofy().save();

		//OfyService.ofy().load().type(Item.class).order("-__key__").filter("owner",yo).first().now();
		ModelAndView model = new ModelAndView("items");
		JSONObject response = new JSONObject();
		
		JSONObject jsonRequest = new JSONObject();		
		JSONParser jsonParser = new JSONParser();
		long userid;		
		long  id;
		String shortDescription = "";
		String description = "";
		
		try{
			jsonRequest = (JSONObject) jsonParser.parse(request);
			
			if (jsonRequest.get("userid")!=null)
				userid = Long.valueOf(jsonRequest.get("userid").toString());			
			else{
				response.put("Error", "Falta User ID");
				return model;
			}
			
			if (jsonRequest.get("id")!=null)
				id = Long.valueOf(jsonRequest.get("id").toString());
			else{
				response.put("Error", "Falta ID del Item");
				return model;
			}
				
			if (jsonRequest.get("shortDescription")!=null)
				shortDescription = jsonRequest.get("shortDescription").toString();
			
			if (jsonRequest.get("description")!=null)
				description = jsonRequest.get("description").toString();
			
			Usuario owner = this.usuarioService.getUsuariosById(userid);
			/*Item lastItem = OfyService.ofy().load().type(Item.class).order("-__key__").filter("owner",owner).first().now();			
			if (lastItem!=null)
				id = lastItem.getId() + 1;*/
			
			Item newItem = new Item();
			newItem.setId(id);
			newItem.setShortDescription(shortDescription);
			newItem.setDescription(description);			
			newItem.setOwner(owner);			
			this.itemService.saveItem(newItem);

			if (this.itemService.getItemsById(id)!=null){
				response.put("PUT", "OK");
				response.put("id", id);
			}else
				response.put("PUT", "ERROR");
			
			model.addObject("response", response);
			
		}catch (Exception e){
			System.out.println(e.toString());
		}

		return model;
		
	}

	@Consumes(value = "application/json")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	/*String createItem(	@RequestParam(value = "id") String id,
						@RequestParam(value = "short_description") String shortDescription,
						@RequestParam(value = "description") String description,
						@RequestParam(value = "image") String image,
						@RequestParam(value = "permalink") String permalink,
						@RequestParam(value = "owner") String ownerId,
						@RequestParam(value = "category") String category,
						@RequestParam(value = "thumbnail") String thumbnail) {*/
		String createItem(@RequestBody String request) {
		
		ModelAndView model = new ModelAndView("items");
		JSONObject response = new JSONObject();
		
		JSONObject jsonRequest = new JSONObject();		
		JSONParser jsonParser = new JSONParser();
		Item newItem = new Item();
		Usuario owner = new Usuario();
		
		long userid;		
		long  id = 0;		
		String shortDescription = "";
		String description = "";
		String image = "";
		String permalink = "";
		String thumbnail = "";
		String category = "";
		
		try{
			jsonRequest = (JSONObject) jsonParser.parse(request);
			
			if (jsonRequest.get("owner")!=null){
				userid = Long.valueOf(jsonRequest.get("owner").toString());				
			}else{				
				return "Error falta el user ID";
			}
			
			owner = this.usuarioService.getUsuariosById(userid);
			newItem.setOwner(owner);		
			
			if (jsonRequest.get("id")!=null){			
				
				id = Long.valueOf(jsonRequest.get("id").toString());
			}else{				
				//Genero ID a partir del ultimo
				Item lastItem = OfyService.ofy().load().type(Item.class).order("-__key__").first().now();			
				
				if (lastItem!=null)
					id = lastItem.getId() + 1;
				else
					return "Error";
				
			}
			
			newItem.setId(id);
			
			if (jsonRequest.get("shortDescription")!=null){
				shortDescription = jsonRequest.get("shortDescription").toString();
				newItem.setShortDescription(shortDescription);
			}
			
			if (jsonRequest.get("description")!=null){
				description = jsonRequest.get("description").toString();
				newItem.setDescription(description);
			}
			
			if (jsonRequest.get("image")!=null){
				image = jsonRequest.get("image").toString();
				Blob imageToBlob = new Blob(image.getBytes());
				newItem.setImage(imageToBlob);
			}
			
			if (jsonRequest.get("permalink")!=null){
				permalink = jsonRequest.get("permalink").toString();
				newItem.setPermalink(permalink);
			}
			
			if (jsonRequest.get("thumbnail")!=null){
				thumbnail = jsonRequest.get("thumbnail").toString();
				newItem.setThumbnail(thumbnail);
			}
			
			if (jsonRequest.get("category")!=null){
				category = jsonRequest.get("category").toString();
				String[] arrCategory = {category};
				newItem.setCategory(arrCategory);
			}
			
			this.itemService.saveItem(newItem);
			
		}catch (Exception e){
			System.out.println(e.toString());
			
			return e.toString() + id + " " + request;
		}
		
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
			item.getOwner().setPassword(""); //por seguridad no se muestra este dato
			items.add(item);
		}

		return items;
	}
	
	/**
	 * Obtiene un item
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Object> getItem(@PathVariable(value = "itemId") String itemId) {
		Item item = new Item();		

		item = this.itemService.getItemsById(Long.valueOf(itemId));
		
		if (item!=null)
			return new ResponseEntity<Object>(item,HttpStatus.OK);
		else{
			JSONObject error=new JSONObject();
			error.put("Error","El Item  ID: " + itemId + " no existe");	
			error.put("Code", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<Object>(error,HttpStatus.BAD_REQUEST);
		}
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

	@RequestMapping(value = "{itemId}/share", method = RequestMethod.POST)
	//public String share(@PathVariable("itemId") String itemid) {
	public @ResponseBody String share(@PathVariable("itemId") String itemid) {
		
		//return "items/share";
		return "El Item " + itemid + " debe compartirse en un ambiente dentro de Facebook";
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
