package com.utn.tacs.tit4tat.tests;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.utn.tacs.tit4tat.meli.MercadoLibre;
import com.utn.tacs.tit4tat.model.Item;

public class RestfulTest {

	@Test
	public void testUrlPostLogin() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/login");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void testUrlGetItems() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items/list");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void testUrlPutItems() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items/edit/1");			
			ClientResponse response = webResource.put(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void testUrlPostItems() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items/create/1");			
			ClientResponse response = webResource.post(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	@Test
	public void testUrlGetAmigosItems() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/friends/items");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	@Test
	public void testUrlPostNotifications() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications/create/1");			
			ClientResponse response = webResource.post(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void testUrlPutNotifications() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications/edit/1");			
			ClientResponse response = webResource.put(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	@Test
	public void testUrlPutNotificationsRechazado() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications/1/accepted");			
			ClientResponse response = webResource.put(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	@Test
	public void testUrlPutNotificationsAceptado() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications/1/rejected");			
			ClientResponse response = webResource.put(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	@Test	
	public void testUrlGetItemShare() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items/1/share");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	@Test	
	public void testUrlGetNotificationsShare() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications/1/share");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	@Test	
	public void testUrlGetNotifications() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications/list");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}

	@Test	
	public void testUrlDeleteItems() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items/delete/1");			
			ClientResponse response = webResource.delete(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
}
