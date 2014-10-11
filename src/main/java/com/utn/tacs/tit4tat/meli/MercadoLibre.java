package com.utn.tacs.tit4tat.meli;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mercadolibre.sdk.AuthorizationFailure;
import com.mercadolibre.sdk.Meli;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.utn.tacs.tit4tat.model.Item;

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
		
		//authorize();
				
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
	
	public String[] getCategory(String value){
		Response response = null;
		String[] categories = null; 
		
		try{
			response = meli.get("/categories/"+value);		
			
			String body = response.getResponseBody();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
			// get a String from the JSON object
			JSONArray category_tree = (JSONArray) jsonObject.get("path_from_root");
			
			categories = new String[category_tree.size()];
			
			Iterator<?> it = category_tree.iterator();
			int index = 0;
			while(it.hasNext()) {
				
				JSONObject element = (JSONObject) it.next();
				String category = (String) element.get("name");
				categories[index] = category;
				index++;

			}
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return categories;
	}
	
	public Response getItem(String value){
		Response response = null;
		try{
			response = meli.get("/items/"+value);		
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
	
	@SuppressWarnings("unchecked")
	public JSONObject searchItems(String query){
		params = new FluentStringsMap();
		params.add("q", query);	
		Response items=null;
		JSONObject response= new JSONObject();
		
		try{
			
			items = meli.get("/sites/MLA/search", params);	
			String body = items.getResponseBody();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
			
			JSONArray results = (JSONArray) jsonObject.get("results");		
			JSONArray itemsArray = new JSONArray();
			Iterator<?> it = results.iterator();
			while(it.hasNext()) {
				
				JSONObject element = (JSONObject) it.next();
				JSONObject new_element = new JSONObject();
				new_element.put("id", element.get("id"));				
				new_element.put("category", element.get("category_id"));
				new_element.put("description", element.get("title"));
				new_element.put("image", element.get("thumbnail"));
				new_element.put("permalink", element.get("permalink"));
				itemsArray.add(new_element);
			}
			
			response.put("results", itemsArray);
			response.put("paging", jsonObject.get("paging"));
			response.put("query", jsonObject.get("query"));			
			response.put("site_id", jsonObject.get("site_id"));
			
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return  response;	
	}		
	
	private List<Item> convertItemsList(Response response)
	{
		List<Item> items = new ArrayList<Item>();
		try{
			String body = response.getResponseBody();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
			// get a String from the JSON object
			JSONArray results = (JSONArray) jsonObject.get("results");
			Item item;
			
			Iterator<?> it = results.iterator();
			while(it.hasNext()) {
				item = new Item();
				
				JSONObject element = (JSONObject) it.next();
				String id = (String) element.get("id");
				String thumbnail = (String) element.get("thumbnail");
				String description = (String) element.get("title");
				String category_id = (String) element.get("category_id");
				URL permalink = (URL) element.get("permalink");
				item.setId(normalizeId(id));
				item.setDescription(description);
				item.setImage(getImageAsStream(thumbnail));				
				item.setCategory(getCategory(category_id));
				item.setUrl(permalink);
				
				items.add(item);

			}
		}catch(Exception e){
			//TODO
		}		
		
		return items;
	}
	
	private Long normalizeId(String id)
	{
		return Long.valueOf(id.substring(3, id.length()));
	}
	
	private ByteArrayOutputStream getImageAsStream(String thumbnail){
		ByteArrayOutputStream bis = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			URL url = new URL(thumbnail);
			is = url.openStream ();
			byte[] bytebuff = new byte[4096]; 
			int n;

			while ( (n = is.read(bytebuff)) > 0 ) {
				bis.write(bytebuff, 0, n);
			}
		}catch(Exception e){
			//TODO
		}
		return bis;		
	}
	
}

