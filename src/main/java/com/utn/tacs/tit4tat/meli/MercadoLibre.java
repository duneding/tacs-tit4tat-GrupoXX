package com.utn.tacs.tit4tat.meli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.mercadolibre.sdk.AuthorizationFailure;
import com.mercadolibre.sdk.Meli;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;

public class MercadoLibre {
	
	private static MercadoLibre instance = null;
	private final String APPTACS_URL = "http://tit4tat-tacs.appspot.com/";
	private final String PROP_FILE = "mla.properties";
	
	private Meli meli;
	String token;
	FluentStringsMap params;
	
	public MercadoLibre(){
		try{
		 meli = new Meli(getAppId(), getSecret());
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		authorize();
				
	}
	 
	public static MercadoLibre getInstance() {
		if(instance == null) {
			instance = new MercadoLibre();
		}
		return instance;
	}
	
	private void authorize(){		
		String redirectUrl = meli.getAuthUrl(APPTACS_URL);		    
		Scanner in = new Scanner(System.in);	
				
		try{
			
			token = getToken();
			meli.authorize(token, APPTACS_URL);
			
		}catch (AuthorizationFailure af){
			System.out.println("Ingresar Token nuevo para:");
			System.out.println(redirectUrl);
			token = in.nextLine();		
			setToken(token);				
			in.close();
		}
		params = new FluentStringsMap();
		params.add("access_token", meli.getAccessToken());
	}
	
	private Long getAppId(){	
		return Long.valueOf(getProperties().getProperty("appid"));
	}

	private String getSecret(){				
		return getProperties().getProperty("secret");
	}

	private String getToken(){		
		return getProperties().getProperty("token");
	}

	private Properties getProperties(){		
		File filePropMLA = new File(PROP_FILE);		
		Properties properties = new Properties();
		
		try{
			properties.load(new FileInputStream(filePropMLA));
		}catch (FileNotFoundException e){
			System.out.println(e.toString());
		}catch (IOException e){
			System.out.println(e.toString());
		}
				
		return properties;
		
	}

	private void setToken(String token){		
		File filePropMLA = new File(PROP_FILE);		
		Properties properties = new Properties();
		
		try{
			properties.load(new FileInputStream(filePropMLA));
			properties.setProperty("token",token);
			FileOutputStream os = new FileOutputStream(filePropMLA);
			properties.store(os, "Propiedades MLA");
		}catch (FileNotFoundException e){
			System.out.println(e.toString());
		}catch (IOException e){
			System.out.println(e.toString());
		}		
	}
	
	public Response get(String value, FluentStringsMap params){
		Response response = null;
		try{
			response = meli.get(value, params);		
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return response;
	}	
	
	public Response get(String value){
		Response response = null;
		try{
			response = meli.get(value);		
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return response;
	}	
	
	public Response getUserMe(){
		Response response = null;
		try{
			response = meli.get("/users/me", params);		
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return response;
		
	}
	
	public String getUrlItemsList(){
		String itemsList = getProperties().getProperty("items");
		String urlItems = "/items?ids=" + itemsList;
		
		return urlItems;
	}
	
	public List<Response> getItemFromProp(){
		String urlItems;
		String itemsList = getProperties().getProperty("items");
		String[] list = itemsList.split(",");
		Response response;		
		List<Response> items = new ArrayList<Response>();
				
		for (String item : list) {
			urlItems = "/items/" + item;
			response = this.get(urlItems);
			items.add(response);			
		}
						
		return items;
	}	
	
	public Response searchItems(String query){
		params = new FluentStringsMap();
		params.add("q", query);	
		Response response=null;
		
		try{		
			response = meli.get("/sites/MLA/search", params);		
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return  response;
	
	}		
	
}

