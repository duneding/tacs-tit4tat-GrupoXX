package com.utn.tacs.tit4tat.servlets;

import static com.utn.tacs.tit4tat.objectify.OfyService.ofy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.googlecode.objectify.ObjectifyService;
import com.utn.tacs.tit4tat.model.Usuario;

public class Startup extends HttpServlet{
	
	public void init()throws ServletException{
		
		//Creacion usuario de test solo para DEV
		Usuario testUser;	
		testUser = new Usuario();    	
    	testUser.setId(99l);
    	testUser.setName("test");    	
    		    
    	ObjectifyService.begin();
	    ofy().save().entities(testUser).now();
	      	   	
	}

}
