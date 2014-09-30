package com.utn.tacs.tit4tat.meli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
		 meli = new Meli(getAppIdML(), getSecretML());
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		getAuthorizeML();
				
	}
	 
	public static MercadoLibre getInstance() {
		if(instance == null) {
			instance = new MercadoLibre();
		}
		return instance;
	}
	
	private void getAuthorizeML(){		
		String redirectUrl = meli.getAuthUrl(APPTACS_URL);
	    FileReader fr = null;
	    BufferedReader br = null;		    
		Scanner in = new Scanner(System.in);	
				
		try{
			
			token = getToken();
			meli.authorize(token, APPTACS_URL);
			
		}catch (AuthorizationFailure af){
			System.out.println("Ingresar Token nuevo para:");
			System.out.println(redirectUrl);
			token = in.nextLine();
		
			setToken(token);	
		}
		params = new FluentStringsMap();
		params.add("access_token", meli.getAccessToken());
	}
	
	private Long getAppIdML(){	
		Properties properties = new Properties();
		return Long.valueOf(getPropertiesML().getProperty("appid"));
	}

	private String getSecretML(){		
		Properties properties = new Properties();
		return getPropertiesML().getProperty("secret");
	}

	private String getToken(){		
		Properties properties = new Properties();
		return getPropertiesML().getProperty("token");
	}

	private Properties getPropertiesML(){		
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
	
	public Response get(String value){
		Response response = null;
		try{
			response = meli.get(value, params);			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return response;
	}	
	
	public String getUrlList(){
		Properties properties = new Properties();
		String itemsList = getPropertiesML().getProperty("items");
		//String[] list = itemsList.split(",");
		//https://api.mercadolibre.com/items?ids=MLA523431013,MLA524394956
		String urlItems = "/items?ids=" + itemsList;
		
		return urlItems;
	}
	
}

