package com.utn.tacs.tit4tat.tests;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mercadolibre.sdk.AuthorizationFailure;
import com.mercadolibre.sdk.Meli;
import com.ning.http.client.FluentStringsMap;
import com.ning.http.client.Response;
import com.utn.tacs.tit4tat.facebook.model.Token;


public class AppVoidTest {

//	private static final String NETWORK_NAME = "Facebook";
//	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
//	private static final Token EMPTY_TOKEN = null;
//	private Meli meli;
//	
//	@Before
//	public void setUp() throws Exception {
//		try{
//		 meli = new Meli(getAppIdML(), getSecretML());
//		}catch(Exception e){
//			System.out.println(e.toString());
//		}
//	}
//	
//	@Test
//	public void connectToFacebook() {
			
			// Replace these with your own api key and secret
		    /* String apiKey = "1454789934802984";
		    String apiSecret = "3285646780d70cf7ad4c5e05277e428f";
		    OAuthService service = new ServiceBuilder()
		                                  .provider(FacebookApi.class)
		                                  .apiKey(apiKey)
		                                  .apiSecret(apiSecret)
		                                  .callback("http://tit4tat-tacs.appspot.com/")
		                                  .build();
		    Scanner in = new Scanner(System.in);
	
		    System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		    System.out.println();


		    // Obtain the Authorization URL
		    System.out.println("Fetching the Authorization URL...");
		    String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
		    System.out.println("Got the Authorization URL!");
		    System.out.println("Now go and authorize Scribe here:");
		    System.out.println(authorizationUrl);
		    System.out.println("And paste the authorization code here");
		    System.out.print(">>");
		    Verifier verifier = new Verifier(in.nextLine());
		    System.out.println();
			
			// Trade the Request Token and Verfier for the Access Token
		    System.out.println("Trading the Request Token for an Access Token...");
		    Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
		    System.out.println("Got the Access Token!");
		    System.out.println("(if your curious it looks like this: " + accessToken + " )");
		    System.out.println();

		    // Now let's go and ask for a protected resource!
		    System.out.println("Now we're going to access a protected resource...");
		    OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		    service.signRequest(accessToken, request);
		    Response response = request.send();
		    System.out.println("Got it! Lets see what we found...");
		    System.out.println();
		    System.out.println(response.getCode());
		    System.out.println(response.getBody());

		    System.out.println();
		    System.out.println("Thats it man! Go and build something awesome with Scribe! :)");*/
			
//	}
	
//	@Test
//	public void connectToML() {
//
//		try{		
//			getAuthorizeML();			
//			FluentStringsMap params = new FluentStringsMap();
//			params.add("access_token", meli.getAccessToken());
//			
//			Response response = meli.get("/users/me", params);			
//
//			Assert.assertEquals(200, response.getStatusCode());
//			
//		}catch(Exception e){
//			System.out.println(e.toString());
//		}
//	}
//	
//	@Test
//	public void connectToMLAndGetInformation() {
//
//		try{		
//			getAuthorizeML();			
//			FluentStringsMap params = new FluentStringsMap();
//			params.add("access_token", meli.getAccessToken());
//			
//			Response response = meli.get("/items/MLA521071653", params);			
//			System.out.println(response.getResponseBody());	
//			Assert.assertEquals(200, response.getStatusCode());
//			
//		}catch(Exception e){
//			System.out.println(e.toString());
//		}
//	}
//	
//	private Long getAppIdML(){
//		
//		Properties properties = new Properties();
//		return Long.valueOf(getPropertiesML().getProperty("appid"));
//	}
//	
//	private String getSecretML(){
//		
//		Properties properties = new Properties();
//		return getPropertiesML().getProperty("secret");
//	}
//	
//	private String getToken(){
//		
//		Properties properties = new Properties();
//		return getPropertiesML().getProperty("token");
//	}
//	
//	private Properties getPropertiesML(){
//		String propMLAfileName = "mla.properties";
//		File filePropMLA = new File(propMLAfileName);		
//		Properties properties = new Properties();
//		
//		try{
//			properties.load(new FileInputStream(filePropMLA));
//		}catch (FileNotFoundException e){
//			System.out.println(e.toString());
//		}catch (IOException e){
//			System.out.println(e.toString());
//		}
//				
//		return properties;
//		
//	}
//	
//	private void setToken(String token){
//		String propMLAfileName = "mla.properties";
//		File filePropMLA = new File(propMLAfileName);		
//		Properties properties = new Properties();
//		
//		try{
//			properties.load(new FileInputStream(filePropMLA));
//			properties.setProperty("token",token);
//			FileOutputStream os = new FileOutputStream(filePropMLA);
//			properties.store(os, "Propiedades MLA");
//		}catch (FileNotFoundException e){
//			System.out.println(e.toString());
//		}catch (IOException e){
//			System.out.println(e.toString());
//		}		
//	}
//	
//	private void getAuthorizeML(){
//		String  appTacs = "http://tit4tat-tacs.appspot.com/";
//		String redirectUrl = meli.getAuthUrl(appTacs);
//	    FileReader fr = null;
//	    BufferedReader br = null;		    
//		Scanner in = new Scanner(System.in);	
//		String token;
//		
//		try{
//			
//			token = getToken();
//			meli.authorize(token, appTacs);
//			
//		}catch (AuthorizationFailure af){
//			System.out.println("Ingresar Token nuevo para:");
//			System.out.println(redirectUrl);
//			token = in.nextLine();
//		
//			setToken(token);	
//		}				
//	}
//	
}

