package com.utn.tacs.tit4tat.servlets;

import static com.utn.tacs.tit4tat.objectify.OfyService.ofy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.googlecode.objectify.ObjectifyService;
import com.utn.tacs.tit4tat.model.Usuario;
import com.utn.tacs.tit4tat.security.CustomAuthenticationProvider;

public class Startup extends HttpServlet{
	
	public void init()throws ServletException{
		
		CustomAuthenticationProvider authProvider = CustomAuthenticationProvider.getInstance();
		
		//Creacion usuario de test solo para DEV
		Usuario testUser = new Usuario();   
		long id = 99l;
		String name = "test";
		String password = "testrest";
		
    	testUser.setId(id);
    	testUser.setName(name);    	
    	String passwordEncoded = authProvider.encodePassword(password);
    	testUser.setPassword(passwordEncoded);
    	ObjectifyService.begin();
	    ofy().save().entities(testUser).now();
	      	   	
	}

}
