package com.utn.tacs.tit4tat.tests;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestfulTest {
	
	/*
	 1.   	Como usuario quiero poder registrarme con mi cuenta de Facebook.
			POST /login
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPostLogin() {

		try{							
			Client client = Client.create();	
			JSONObject jsonLogin=new JSONObject();
			jsonLogin.put("user","martin");			
			jsonLogin.put("password","1234");			
			WebResource webResource = client.resource("http://localhost:8888/login");
			ClientResponse response = webResource.
													accept("application/json").
													header("Content-Length", jsonLogin.toJSONString().length()).
													post(ClientResponse.class, jsonLogin.toJSONString());
			
			JSONObject jsResponse;		
			JSONParser jsonParser = new JSONParser();
			
			try{
				jsResponse = (JSONObject) jsonParser.parse(response.getEntity(String.class));
				String result = ((JSONObject) ((JSONObject) jsResponse.get("model")).get("response")).get("login").toString();
				Assert.assertEquals(result, "OK");
				
			}catch (Exception e){
				System.out.println(e.toString());
			}
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/*
	 2.1.  Como usuario quiero poder publicar un item, buscando y seleccionando un artículo publicado en Mercado Libre a modo de referencia.
			 Obtener ítems: GET /items
	 */
	@Test
	public void testGetItems() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/*
	 2.2.   Como usuario quiero poder publicar un item, buscando y seleccionando un artículo publicado en Mercado Libre a modo de referencia.
			 Actualizar ítem: PUT /items  (Datos en el body)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPutItems() {

		try{							
			JSONObject jsonPut=new JSONObject();
			jsonPut.put("item","1");			
			jsonPut.put("description","nueva");	
			
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items");			
			ClientResponse response = webResource.
													accept("application/json").
													header("Content-Length", jsonPut.toJSONString().length()).
													put(ClientResponse.class, jsonPut.toJSONString());

			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/*
	 2.3.   Como usuario quiero poder publicar un item, buscando y seleccionando un artículo publicado en Mercado Libre a modo de referencia.
			 Nuevo ítem: POST /items  (Datos en el body)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPostItems() {

		try{							
			JSONObject jsonPut=new JSONObject();
			jsonPut.put("item","1");			
			jsonPut.put("description","nueva");	
			
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items");			
			ClientResponse response = webResource.
													accept("application/json").
													header("Content-Length", jsonPut.toJSONString().length()).
													post(ClientResponse.class, jsonPut.toJSONString());
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	/*
	 3.    Como usuario quiero ver los items publicados por mis amigos con su descripción e imagen correspondiente.
		    GET /friends/ítems
	 */
	@Test
	public void testGetAmigosItems() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/friends/items");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	/*
	 4.    Como usuario quiero enviar a un amigo una solicitud de trueque indicando un item publicado por él y uno publicado por mi.
		    POST /notifications (Datos en el body)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPostNotification() {

		try{
			
			JSONObject jsonPost=new JSONObject();			
			jsonPost.put("friend","1");	
			jsonPost.put("item_friend","2");
			jsonPost.put("item","1");
			
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications");			
			ClientResponse response = webResource.
													accept("application/json").
													header("Content-Length", jsonPost.toJSONString().length()).
													post(ClientResponse.class, jsonPost.toJSONString());
						
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/*
	 5.    Como usuario quiero poder aceptar o rechazar una solicitud de trueque que me haya sido enviada
		    PUT /notifications (Datos en el body)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPutNotifications() {

		try{										
			JSONObject jsonPut=new JSONObject();			
			jsonPut.put("notification","1");	
			jsonPut.put("estado","accepted");
			
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications/reply");			
			ClientResponse response = webResource.
													accept("application/json").
													header("Content-Length", jsonPut.toJSONString().length()).
													put(ClientResponse.class, jsonPut.toJSONString());
			
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	/*
	 6.    Como usuario quiero compartir la creación de un ítem en Facebook
		    POST /items/{idItem}/share
	 */
	@SuppressWarnings("unchecked")
	@Test	
	public void testGetItemShare() {

		try{							
			JSONObject jsonPost=new JSONObject();			
			jsonPost.put("friend","1");	
			jsonPost.put("item_friend","2");
			jsonPost.put("item","1");
			
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items/1/share");			
			ClientResponse response = webResource.
													accept("application/json").
													header("Content-Length", jsonPost.toJSONString().length()).
													post(ClientResponse.class, jsonPost.toJSONString());
								
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	  	
	/*
	 7.    Como usuario quiero compartir un trueque aceptado en mi muro de Facebook.
		    POST /notifications/{idNotifications}/share
	 */
	@SuppressWarnings("unchecked")
	@Test	
	public void testUrlGetNotificationsShare() {

		try{							
			JSONObject jsonPost=new JSONObject();			
			jsonPost.put("notifications","1");	
			
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications/1/share");			
			ClientResponse response = webResource.
													accept("application/json").
													header("Content-Length", jsonPost.toJSONString().length()).
													post(ClientResponse.class, jsonPost.toJSONString());
								
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	/*
	 8.    Como usuario quiero ver las solicitudes de trueque que me fueron enviadas
		    GET /notifications
	 */
	@Test	
	public void testGetNotifications() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/notifications");			
			ClientResponse response = webResource.get(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/*
	 9.    Como usuario quiero retirar de circulación un item que cree.
		    DELETE /items/{idItem}
	 */
	@Test	
	public void testDeleteItems() {

		try{							
			Client client = Client.create();		 
			WebResource webResource = client.resource("http://localhost:8888/items/1");			
			ClientResponse response = webResource.delete(ClientResponse.class);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
}
