package com.utn.tacs.tit4tat.controller;

import java.util.Iterator;

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
import com.utn.tacs.tit4tat.security.CustomAuthenticationProvider;
import com.utn.tacs.tit4tat.security.Session;
import com.utn.tacs.tit4tat.security.Token;
import com.utn.tacs.tit4tat.service.ItemService;
import com.utn.tacs.tit4tat.service.UsuarioService;

@Controller
public class HomeController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/home", method = { RequestMethod.POST, RequestMethod.GET })
	public String index(ModelMap model, HttpSession httpSession) {
		
		httpSession.setAttribute("userSession", registrySession(0, null, null, "facebook"));
		
		return "home";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Consumes(value ="application/json")
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	//public @ResponseBody ModelAndView login(String request, HttpSession session) {
	public @ResponseBody ModelAndView login(@RequestBody String request, HttpSession httpSession) {
			
		CustomAuthenticationProvider authProvider = CustomAuthenticationProvider.getInstance();
		long userid = -1;
		
		//TODO loggear con datos de usuario, si no existe crear uno
		ModelAndView model = new ModelAndView("home");		
		
		JSONParser jsonParser = new JSONParser();
		try{
			JSONObject jsonRequest = (JSONObject) jsonParser.parse(request);
			String id = jsonRequest.get("userid").toString();
			String password = jsonRequest.get("password").toString();			
			Token token = new Token();
			String username = "";
			
			String passwordEncoded = authProvider.encodePassword(password);;
			Iterator iterator = this.usuarioService.getUsuarios().iterator();
			while(iterator.hasNext()){
				  Usuario user = (Usuario) iterator.next();				  
				  
				  if (user.getId().toString().equals(id) && user.getPassword().equals(passwordEncoded)){
					  userid = user.getId();
					  token = authProvider.calculateToken();
					  break;
				  }
			}
			
			JSONObject obj=new JSONObject();
			if (token.getCode()!=null){						
				obj.put("login","OK");
				obj.put("username",username);
				obj.put("token",token);
	
				httpSession.setAttribute("userSession", registrySession(userid, username, token, "rest"));
			}else
				obj.put("login","no autorizado!");
						
			  
			model.addObject("response", obj);
			
		}catch(Exception e){
			//TODO
			System.out.println(e.toString());
		}
		
		return model;
	}
		
	/*@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleRequest() {

		return new ModelAndView("home");
	}*/

	@RequestMapping(value="/user/{userId}", method=RequestMethod.GET)
	public String getUserInfo(@PathVariable("userId") String userId, Model model) {

		long userIdLong = Long.valueOf(userId);
		Usuario user = this.usuarioService.getUsuariosById(userIdLong);
		model.addAttribute("user", user);
		return "home";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public @ResponseBody void setUser(@RequestParam(value = "userId") String userId,
									  @RequestParam(value = "userName") String userName,
									  HttpSession httpSession) {
				
		Usuario usuario = new Usuario();
		usuario.setId(Long.parseLong(userId));
		usuario.setName(userName);
		
		Usuario persistUser = this.usuarioService.getUsuariosById(usuario.getId());
				
		CustomAuthenticationProvider authProvider = CustomAuthenticationProvider.getInstance();		
		Token token = authProvider.calculateToken();
				
		httpSession.setAttribute("userSession", registrySession(usuario.getId(), userName, token, "facebook"));
				
		this.usuarioService.saveUsuario(usuario);
		
		if (persistUser != null) {
			return;
		}
	}
	
	private Session registrySession(long userid, String username, Token token, String scope){
		Session session = Session.getInstance();
		session.setToken(token);
		session.setUserid(userid);
		session.setUsername(username);
		session.setScope(scope);
		
		return session;
	}

}
