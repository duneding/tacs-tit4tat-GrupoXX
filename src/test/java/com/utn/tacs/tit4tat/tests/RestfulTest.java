package com.utn.tacs.tit4tat.tests;

import javax.ws.rs.core.Cookie;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.utn.tacs.tit4tat.security.Token;

@SuppressWarnings({"unused", "unchecked"})
public class RestfulTest {
	    
    private Token token = new Token();
    private final String appEnv = "http://localhost:8888/";
    //private final String appEnv = "http://t4t-tacs.appspot.com/";
    private final String userId = "99";
    private final String friendId = "98";
    private final String password = "testrest";
    private final String ACCEPTED = "acepted";
    private String solCreada = "";
    ClientResponse loginResponse;
    JSONObject jsonLogin;
    
    
	/*
	 1.   	Como usuario quiero poder registrarme con mi cuenta de Facebook.
			POST /login
			
			Request:
			{
				"userid": "99",
				"password": "testrest"
			}
			
	 */    
    @Before
	public void testPostLogin(){	
		loginResponse = login(userId,password);
    	jsonLogin = getLoginResponse(loginResponse);
    	String code = ((JSONObject)jsonLogin.get("token")).get("code").toString();
    	//long expiryTime = Long.valueOf(((JSONObject)jsonLogin.get("token")).get("expiryTime").toString());    	
    	
    	token.setCode(code);        
    	//token.setExpiryTime(expiryTime);
    	
    	Assert.assertTrue(token.getCode()!="");
    	//Assert.assertTrue(token.getExpiryTime()>0);
    	
    }
    
    @Test
    public void testLoginOK() {    	    	
    	Assert.assertEquals( jsonLogin.get("status").toString(), "200");							
    }
    
    @Test
    public void testLoginFailed() {
    	JSONObject jsonLogin = getLoginResponse(login("97","testrdsest"));    	
    	Assert.assertEquals( jsonLogin.get("status").toString(), "401");						
    }
    
	/*
	 2.1.  Como usuario quiero poder publicar un item, buscando y seleccionando un artículo publicado en Mercado Libre a modo de referencia.
			 Obtener ítems: GET /items
	 */
	@Test
	public void testGetItems() {
		
		try{
	    	String token_param = "token=" + token.getCode();
	    	String user_param = "userId=" + userId;
	    	String params = "?" + user_param + "&" + token_param;
	    	
	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "items"+params);			
			ClientResponse response = GET(webResource, cookie);
			
			Assert.assertEquals(200, response.getStatus());
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
		
	/*
	 2.2.   Como usuario quiero poder publicar un item, buscando y seleccionando un artículo publicado en Mercado Libre a modo de referencia.
			 Actualizar ítem: PUT /items  (Datos en el body)
	 */
	@Test
	public void testPutItems() {

		try{						
	    	
			JSONObject request=new JSONObject();
			request.put("token",token.getCode());			
			request.put("userid",userId);
			request.put("id","1");			
			request.put("description","kindle con mas de 100 libros");
			request.put("shortDescription","kindle");
			
	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "items");			
			ClientResponse response = PUT(webResource, cookie, request);
			
			Assert.assertEquals(200, response.getStatus());
						
			//Friend
			request.put("token",token.getCode());			
			request.put("userid",friendId);
			request.put("id","6");			
			request.put("description","ipad super de la vida loca");
			request.put("shortDescription","ipad");
						
			response = PUT(webResource, cookie, request);
			
			Assert.assertEquals(200, response.getStatus());
			
						
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/*
	 2.3.   Como usuario quiero poder publicar un item, buscando y seleccionando un artículo publicado en Mercado Libre a modo de referencia.
			 Nuevo ítem: POST /items  (Datos en el body)
	 */	
	@Test
	public void testPostItems() {

		try{							
	    	
	    	JSONObject request=new JSONObject();
			request.put("token",token.getCode());			
			request.put("userid",userId);	
			request.put("description","kindle con mas de 100 libros");
			request.put("shortDescription","kindle");

	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "items");			
			ClientResponse response = POST(webResource, cookie, request);

			Assert.assertEquals(200, response.getStatus());
			System.out.println(response.getEntity(String.class));
			
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
		    	
		    	String token_param = "token=" + token.getCode();
		    	String user_param = "idFriends=" + userId + "," + friendId;
		    	String params = "?" + user_param + "&" + token_param;
		    	
		    	Cookie cookie = getCookieLogin(loginResponse);
		    	WebResource webResource = getResourceRest(loginResponse.getClient(), "friends/items"+params);			
				ClientResponse response = GET(webResource, cookie);

				Assert.assertEquals(200, response.getStatus());
				System.out.println(response.getEntity(String.class));
				
			}catch(Exception e){
				System.out.println(e.toString());
			}
						
	}	
	
	/*
	 4.    Como usuario quiero enviar a un amigo una solicitud de trueque indicando un item publicado por él y uno publicado por mi.
		    POST /notifications (Datos en el body)
	 */	
	@Test
	public void testPostNotification() {

		try{
			
	    	JSONObject request=new JSONObject();
			request.put("token",token.getCode());			
			request.put("owner_id",userId);	
			request.put("item_id","7");
			request.put("user_id",friendId);
			request.put("user_item_id","6");
			request.put("message","dale copate y cambiame el item");
			
	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "notifications");			
			ClientResponse response = POST(webResource, cookie, request);
			
			Assert.assertEquals(200, response.getStatus());
			solCreada = response.getEntity(String.class).toString();
			System.out.println("Solicitud creada " + solCreada);
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/*
	 5.    Como usuario quiero poder aceptar o rechazar una solicitud de trueque que me haya sido enviada
		    PUT /notifications/{idNotifications}/reply (Datos en el body)
	 */
	@Test
	public void testPutNotifications() {

		try{				
			testPostNotification();			
	    	
	    	JSONObject request=new JSONObject();
	    	request.put("token",token.getCode());
	    	request.put("id",solCreada);	
	    	request.put("state",ACCEPTED);	    	
	    	
	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "notifications");			
			ClientResponse response = PUT(webResource, cookie, request);

			Assert.assertEquals(200, response.getStatus());
			System.out.println(response.getEntity(String.class));

			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	
	/*
	 6.    Como usuario quiero compartir la creación de un ítem en Facebook
		    POST /items/{idItem}/share
	 */
	@Test	
	public void testPostItemShare() {

		try{
	    	
	    	JSONObject request=new JSONObject();
			request.put("token",token.getCode());			

	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "items/1/share");			
			ClientResponse response = POST(webResource, cookie, request);

			Assert.assertEquals(200, response.getStatus());
			System.out.println(response.getEntity(String.class));
						
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}	
	  	
	/*
	 7.    Como usuario quiero compartir un trueque aceptado en mi muro de Facebook.
		    POST /notifications/{idNotifications}/share
	 */
	@Test	
	public void testPostNotificationsShare() {

		try{
	    	
	    	JSONObject request=new JSONObject();
			request.put("token",token.getCode());			

	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "notifications/1/share");			
			ClientResponse response = POST(webResource, cookie, request);

			Assert.assertEquals(200, response.getStatus());
			System.out.println(response.getEntity(String.class));
						
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

	    	String token_param = "token=" + token.getCode();
	    	String user_param = "userId=" + userId;
	    	String params = "?" + user_param + "&" + token_param;
	    	
	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "notifications"+params);			
			ClientResponse response = GET(webResource, cookie);
			
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
	    	
	    	JSONObject request=new JSONObject();
			request.put("token",token.getCode());			

	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "items/1");			
			ClientResponse response = DELETE(webResource, cookie, request);

			Assert.assertEquals(200, response.getStatus());
			System.out.println(response.getEntity(String.class));
			
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	//TEST ADICIONALES/COMPLEMENTARIOS a las historias
	/*
	 Consultar Item: error 400
	 Creacion Item: OK 
	 */
	@Test
	public void testCreacionItems() {

		try{						
	    	
	    	String token_param = "token=" + token.getCode();	    	
	    	String params = "?" + token_param;
	    	String itemId = "1601";
	    	
	    	Cookie cookie = getCookieLogin(loginResponse);
	    	WebResource webResource = getResourceRest(loginResponse.getClient(), "items/"+itemId+params);			
			ClientResponse response = GET(webResource, cookie);			
			
			Assert.assertEquals(400, response.getStatus()); //No esta creado
			
			JSONObject request=new JSONObject();
			request.put("token",token.getCode());			
			request.put("userid",userId);
			request.put("id",itemId);			
			request.put("description","kindle con mas de 100 libros");
			request.put("shortDescription","kindle");
			
	    	cookie = getCookieLogin(loginResponse);
	    	webResource = getResourceRest(loginResponse.getClient(), "items");			
			response = PUT(webResource, cookie, request);
			
			Assert.assertEquals(200, response.getStatus()); //Esta creado
			
			
	    	request=new JSONObject();
			request.put("token",token.getCode());			

	    	cookie = getCookieLogin(loginResponse);
	    	webResource = getResourceRest(loginResponse.getClient(), "items/"+itemId);			
			response = DELETE(webResource, cookie, request);

			Assert.assertEquals(200, response.getStatus()); //Se borra
								
						
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		Metodos adicionales para los test
		------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	private ClientResponse login(String userid, String password){	
		JSONObject request=new JSONObject();

		request.put("userid",userid);			
		request.put("password",password);			
		WebResource webResource = getResourceRest("login");
		ClientResponse clientResponse = POST(webResource,request);
		return clientResponse;

    }
	
	private JSONObject getToken(JSONObject jsonLogin){
		return (JSONObject) jsonLogin.get("token");
	}
	
	private String getCode(JSONObject jsonToken){
		return jsonToken.get("code").toString();
	}
	
	private long getExpiryTime(JSONObject jsonToken){
		return Long.valueOf(jsonToken.get("expiryTime").toString());
	}
	
    private Builder getBuilderRest(WebResource webResource, Cookie cookie, JSONObject request){
    	
    	Builder builder = webResource.
								   accept("application/json").
								   header("Content-Length", request.toJSONString().length()); 
    	if (cookie!=null)
    		return builder.cookie(cookie);
    	else
    		return builder;
		
    }
    
    private Builder getBuilderRest(WebResource webResource, JSONObject request){    	
    	return getBuilderRest(webResource, null, request);
    }
    
    private ClientResponse POST(WebResource webResource, JSONObject request){
    	
		return POST(webResource, null, request);
    }
    
    private ClientResponse POST(WebResource webResource, Cookie cookie, JSONObject request){
    	
    	if (cookie!=null)
    		return getBuilderRest(webResource, cookie, request).
    				   post(ClientResponse.class, request.toJSONString());
    	else    		
    		return getBuilderRest(webResource, request).
    				post(ClientResponse.class, request.toJSONString());
    }
    
    private ClientResponse PUT(WebResource webResource, Cookie cookie, JSONObject request){
       	
    	return getBuilderRest(webResource, cookie, request).
    				   put(ClientResponse.class, request.toJSONString());    	
    }
    
    private ClientResponse GET(WebResource webResource, Cookie cookie){
    	return webResource.cookie(cookie).
				get(ClientResponse.class);
    }
    
    private ClientResponse DELETE(WebResource webResource, Cookie cookie, JSONObject request){
       	
    	return getBuilderRest(webResource, cookie, request).
    				delete(ClientResponse.class);
    	
    }
	
    private JSONObject getLoginResponse(ClientResponse clientResponse){
		JSONObject response = new JSONObject();		
		JSONParser jsonParser = new JSONParser();
    	try{
			
			if (clientResponse.getStatus()==401)
				response.put("status", 401);
			else{
				response = (JSONObject) jsonParser.parse(clientResponse.getEntity(String.class));
				response = ((JSONObject) ((JSONObject) response.get("model")).get("response"));
				response.put("status", clientResponse.getStatus());
			}
							
		}catch(Exception e){
			System.out.println(e.toString());			
		}
		
		return response;
    }
    
    private String getEnv(){
    	return appEnv;
    }
    
    private WebResource getResourceRest(String path){
    	Client client = Client.create();
    	return client.resource(getEnv()+path);
    }

    private WebResource getResourceRest(Client client, String path){    	
    	return client.resource(getEnv()+path);
    }
    
    private Cookie getCookieLogin(ClientResponse clientResponse){
    	return clientResponse.getCookies().get(0);
    }
}
