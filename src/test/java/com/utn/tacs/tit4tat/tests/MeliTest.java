package com.utn.tacs.tit4tat.tests;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ning.http.client.Response;
import com.utn.tacs.tit4tat.facebook.model.Token;
import com.utn.tacs.tit4tat.meli.MercadoLibre;
import com.utn.tacs.tit4tat.model.Item;


public class MeliTest {

	private static final String NETWORK_NAME = "Facebook";
	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
	private static final Token EMPTY_TOKEN = null;	
		
	/*@Test
	public void testConnectToML() {
		
		try{
			MercadoLibre ml_connection = MercadoLibre.getInstance();		
			ml_connection.authorize();
			Response response = ml_connection.getUserMe();			

			Assert.assertEquals(200, response.getStatusCode());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}*/
	
//	@Test
//	public void testGetInformation() {
//
//		try{							
//			MercadoLibre ml_connection = MercadoLibre.getInstance();								
//			Response response = ml_connection.getItemMeli("MLA521071653");			
//			System.out.println(response.getResponseBody());	
//			Assert.assertEquals(200, response.getStatusCode());
//			
//		}catch(Exception e){
//			System.out.println(e.toString());
//		}
//	}
//	
//	@Test
//	public void testListItemFromProperties() {
//
//		try{							
//			MercadoLibre ml_connection = MercadoLibre.getInstance();								
//			List<Response> list = ml_connection.getItemFromProp();
//			Assert.assertTrue(list.size()>0);
//			
//		}catch(Exception e){
//			System.out.println(e.toString());
//		}
//	}	
//		
//	@Test
//	public void testSearchJsonItemsML() {
//		
//		try{
//			MercadoLibre ml_connection = MercadoLibre.getInstance();			
//			JSONObject items = ml_connection.searchJSONItems("ipod");
//
//			Assert.assertTrue(items.size()>0);
//			
//			}catch(Exception e){
//				System.out.println(e.toString());
//			}
//	}
//	
//	@Test
//	public void testSearchListItemsML() {
//		
//		try{
//			MercadoLibre ml_connection = MercadoLibre.getInstance();			
//			List<Item> items = ml_connection.searchlListItems("ipod");
//
//			Assert.assertTrue(items.size()>0);
//			
//			}catch(Exception e){
//				System.out.println(e.toString());
//			}
//	}	
//	
//	@Test
//	public void testSearchListItemsJersey() {
//		
//		try{
//			MercadoLibre ml_connection = MercadoLibre.getInstance();			
//			JSONObject items = ml_connection.searchJSONItems("ipod");
//
//			Assert.assertTrue(items.size()>0);
//			
//			}catch(Exception e){
//				System.out.println(e.toString());
//			}
//	}
//	
//	@Test
//	public void testGetItemJersey() {
//
//		try{							
//			MercadoLibre ml_connection = MercadoLibre.getInstance();								
//			Item response = ml_connection.getItem("MLA521071653");					
//			Assert.assertTrue(response.getId()>0);
//			
//		}catch(Exception e){
//			System.out.println(e.toString());
//		}
//	}
}

