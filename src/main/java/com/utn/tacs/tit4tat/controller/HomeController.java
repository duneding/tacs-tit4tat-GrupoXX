package com.utn.tacs.tit4tat.controller;

import java.util.List;

import javax.ws.rs.Consumes;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.utn.tacs.tit4tat.model.Item;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.service.ItemService;
import com.utn.tacs.tit4tat.service.UsuarioService;

@Controller
public class HomeController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String index(ModelMap model) {
		return "home";
	}

	@SuppressWarnings("unchecked")
	@Consumes(value ="application/json")
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public @ResponseBody ModelAndView login(@RequestBody String jsonRequest) {
		
		//TODO loggear con datos de usuario, si no existe crear uno
		ModelAndView model = new ModelAndView("home");		
		
		JSONObject obj=new JSONObject();
		obj.put("login","OK");
		/*StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonText = out.toString();*/
		  
		model.addObject("response", obj);
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() {

		return new ModelAndView("home");
	}
	
	/*@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String signed_request(ModelMap model) {
		return "home";
	}*/

	@RequestMapping(value="/user/{userId}", method=RequestMethod.GET)
	public String getUserInfo(@PathVariable("userId") String userId, Model model) {

		Long userIdLong = Long.getLong(userId);
		Usuario user = this.usuarioService.getUsuariosById(userIdLong);
		model.addAttribute("user", user);
		return "home";
	}

	@RequestMapping(value = "/user/{userId}/items", method = RequestMethod.GET)
	public String getItemsByUser(@PathVariable("userId") String userId,
			Model model) {

		Long userIdLong = Long.getLong(userId);
		List<Item> items = this.itemService.getItemsByUser(userIdLong);
		model.addAttribute("items", items);

		return "home";
	}

	@RequestMapping(value = "/test/persistUsers", method = RequestMethod.GET)
	public String executeTest(Model model) {

//		Usuario user1 = new Usuario("Martin Dagostino");

//		this.usuarioService.saveUsuario(user1);

		for (Usuario usuario : this.usuarioService.getUsuarios()) {
			System.out.println(usuario);
//			this.usuarioService.deleteUsuario(usuario);
		}

		return "home";
	}
}
