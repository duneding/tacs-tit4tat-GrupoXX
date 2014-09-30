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
import com.utn.tacs.tit4tat.meli.MercadoLibre;


public class AppVoidTest {

	private static final String NETWORK_NAME = "Facebook";
	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
	private static final Token EMPTY_TOKEN = null;	
	
	@Before
	public void setUp() throws Exception {

	}
	
	@Test
	public void connectToFacebook() {
			
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
			
	}
	
	@Test
	public void connectToML() {
		
		try{
			MercadoLibre ml_connection = MercadoLibre.getInstance();								
			Response response = ml_connection.get("/users/me");			

			Assert.assertEquals(200, response.getStatusCode());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void connectToMLAndGetInformation() {

		try{							
			MercadoLibre ml_connection = MercadoLibre.getInstance();								
			Response response = ml_connection.get("/items/MLA521071653");			
			System.out.println(response.getResponseBody());	
			Assert.assertEquals(200, response.getStatusCode());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void listItemFromPropertiesML() {

		try{							
			MercadoLibre ml_connection = MercadoLibre.getInstance();								
			String urlList = ml_connection.getUrlList();			
			Response response = ml_connection.get(urlList);
			Assert.assertEquals(200, response.getStatusCode());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
		
}

