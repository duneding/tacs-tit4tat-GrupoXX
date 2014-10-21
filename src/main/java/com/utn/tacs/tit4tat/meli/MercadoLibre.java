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

import com.google.appengine.api.datastore.Blob;
import com.mercadolibre.sdk.AuthorizationFailure;
import com.mercadolibre.sdk.Meli;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.utn.tacs.tit4tat.model.Item;

import org.apache.commons.io.IOUtils;

public class MercadoLibre {
	
	private static MercadoLibre instance = null;
	private final String APPTACS_URL = "http://tit4tat-tacs.appspot.com/";
	private final String PROP_FILE = "mla.properties";
	private final String URL_API = "https://api.mercadolibre.com";
	private final String URL_SEARCH =	"/sites/MLA/search?q=";
	private final String URL_ITEMS = "/items/";
	private final String URL_CATEGORIES = "/categories/";
	
	private Meli meli;
	String token;
	FluentStringsMap params;
	
	public MercadoLibre(){
		try{
		 //meli = new Meli(getAppId(), getSecret());
			meli = new Meli(8197151284077741L, "NEPHqIlkSkVD5GluZF9icEOx2TMwK0lK");
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
	
	public void authorize(){		
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
			//response = meli.get("/categories/"+value);		
			JSONParser jsonParser = new JSONParser();
			
			Client client = Client.create();		 
			WebResource webResource = client.resource(URL_API + URL_CATEGORIES + value);			
			String responseGet = webResource.accept("application/json").get(String.class);
			JSONObject jsonObject = (JSONObject) jsonParser.parse(responseGet);
		
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
	
	public Response getItemMeli(String value){
		Response response = null;
		try{
			response = meli.get("/items/"+value);		
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return response;
	}

	private JSONObject getItemJSon(String value){
		JSONObject response = null;
		
		try{		
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = null;
			
			Client client = Client.create();		 
			WebResource webResource = client.resource(URL_API + URL_ITEMS + value);			
			String responseGet = webResource.accept("application/json").get(String.class);
			response = (JSONObject) jsonParser.parse(responseGet);			

		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return response;
	}
	
	public Item getItem(String value){
		Item item = new Item();
		try{		
			JSONParser jsonParser = new JSONParser();
						
			Client client = Client.create();		 
			WebResource webResource = client.resource(URL_API + URL_ITEMS + value);			
			String responseGet = webResource.accept("application/json").get(String.class);
			JSONObject jsonObject = (JSONObject) jsonParser.parse(responseGet);
			item = ConvertJsonToItem(jsonObject);

		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		return item;
	}
	
	private Item ConvertJsonToItem(JSONObject jsonObject){		
		Item item = new Item();
		try{										
			URL permalink = new URL(jsonObject.get("permalink").toString());
			item.setId(normalizeId((String) jsonObject.get("id")));
			item.setDescription((String) jsonObject.get("title"));
			item.setImage(getImageAsBlob((String) jsonObject.get("thumbnail")));				
			item.setCategory(getCategory((String) jsonObject.get("category_id")));
			item.setPermalink(permalink);			

		}catch(Exception e){
			System.out.println(e.toString());
		}		
		
		return item;
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
	
	private JSONObject getJSONObject(String body){
		JSONParser jsonParser = new JSONParser();
		JSONObject result = null;
		
		try{
			result = (JSONObject) jsonParser.parse(body);
		}catch (Exception e){
			//TODO
		}
		
		return result;
	}

	public JSONObject searchJSONItems(String query){
		
		//return getJSONResponse(searchItemsMeli(query));
		return getJSONResponse(searchItemsJersey(query));
	}		
	
	public List<Item> searchlListItems(String query){
		
		//return getListResponse(searchItemsMeli(query));
		return getListResponse(searchItemsJersey(query));

	}	
	
	private JSONObject searchItemsJersey(String query){
		JSONParser jsonParser = new JSONParser();
		JSONObject response = null;
		
		Client client = Client.create();		 
		WebResource webResource = client.resource(URL_API + URL_SEARCH + query);
		
		String responseGet = webResource.accept("application/json").get(String.class);
		
		try{
			response = (JSONObject) jsonParser.parse(responseGet);
		}catch (Exception e){
			System.out.println(e.toString());
		}
		
	
		return response;
		
	}
	
	private JSONObject searchItemsMeli(String query){
		params = new FluentStringsMap();
		params.add("q", query);	
		Response items=null;
		JSONObject response = null;
		
		try{
			items = meli.get("/sites/MLA/search", params);	
			String body = items.getResponseBody();
			response = getJSONObject(body);
			
		}catch (Exception e){
			//TODO
			System.out.println(e.toString());
		}
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject getJSONResponse(JSONObject jsonObject){
		JSONObject response= new JSONObject();
		
		try{			
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
		
		return response;
	}
	
	private List<Item> getListResponse(JSONObject jsonObject){
		List<Item> items = new ArrayList<Item>();
		int limit = 0;
		try{		
			JSONArray results = (JSONArray) jsonObject.get("results");			
			Item item;
			Iterator<?> it = results.iterator();
			while(it.hasNext()) {
				item = new Item();
				
				JSONObject element = (JSONObject) it.next();			
				URL permalink = new URL(element.get("permalink").toString());
				item.setId(normalizeId((String) element.get("id")));
				item.setDescription((String) element.get("title"));
				item.setImage(getImageAsBlob((String) element.get("thumbnail")));				
				item.setCategory(getCategory((String) element.get("category_id")));
				item.setPermalink(permalink);
				
				items.add(item);
				
				limit += 1;
				
				if (limit>5)
					break;

			}
		}catch(Exception e){
			System.out.println(e.toString());
		}		
		
		return items;
	}
	
	private Long normalizeId(String id)
	{
		return Long.valueOf(id.substring(3, id.length()));
	}
	
	private byte[] getImageAsBytes(String thumbnail){
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
			System.out.println(e.toString());
		}
		
		return bis.toByteArray();		
	}
	
	private Blob getImageAsBlob(String thumbnail){
		ByteArrayOutputStream bis = new ByteArrayOutputStream();
		InputStream is = null;
		Blob result = null;
		try {
			URL url = new URL(thumbnail);
			is = url.openStream ();
			byte[] bytebuff = new byte[4096]; 
			int n;
			result = new Blob(IOUtils.toByteArray(is));
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		
		return result;		
	}	
	
}

