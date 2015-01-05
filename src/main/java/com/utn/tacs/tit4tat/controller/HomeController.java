package com.utn.tacs.tit4tat.controller;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String go(ModelMap model) {
		return "home";
	}
	
	@SuppressWarnings("unchecked")
	@Consumes(value ="application/json")
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ModelAndView login(String request, HttpSession session) {
		
		//TODO loggear con datos de usuario, si no existe crear uno
		ModelAndView model = new ModelAndView("home");		

		JSONParser jsonParser = new JSONParser();
		try{
			JSONObject jsonRequest = (JSONObject) jsonParser.parse(request);
			String username = jsonRequest.get("username").toString();
			String password = jsonRequest.get("password").toString();
			
			JSONObject obj=new JSONObject();		
			obj.put("login","OK");
			obj.put("username",username);
			obj.put("password",password);
			obj.put("token","test");
			
			session.setAttribute("user", username);			  
			model.addObject("response", obj);
			
		}catch(Exception e){
			//TODO
		}
		
		return model;
	}
		
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() {

		return new ModelAndView("home");
	}

	@RequestMapping(value="/user/{userId}", method=RequestMethod.GET)
	public String getUserInfo(@PathVariable("userId") String userId, Model model) {

		Long userIdLong = Long.getLong(userId);
		Usuario user = this.usuarioService.getUsuariosById(userIdLong);
		model.addAttribute("user", user);
		return "home";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody void setUser(@RequestParam(value = "userId") String userId,
			@RequestParam(value = "userName") String userName) {
		
		Usuario usuario = new Usuario();
		usuario.setId(Long.parseLong(userId));
		usuario.setName(userName);
		
		Usuario persistUser = this.usuarioService.getUsuariosById(usuario.getId());
		
		if (persistUser != null) {
			return;
		}
		
		this.usuarioService.saveUsuario(usuario);
	}

}
