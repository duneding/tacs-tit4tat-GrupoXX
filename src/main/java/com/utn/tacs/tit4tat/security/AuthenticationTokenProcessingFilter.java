package com.utn.tacs.tit4tat.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
	
	private final String API = "api";
	private final String FACEBOOK = "facebook";
	private final String uAPI = "/login";
	private final String uFACEBOOK = "/user";

    @SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	
        HttpServletRequest httpRequest = (HttpServletRequest) request;     
        MultiReadHttpServletRequest wrapper = new MultiReadHttpServletRequest( httpRequest );
        Map<String, String[]> parms = request.getParameterMap();
        
    	String scope = "";
    	Authenticator authenticator = Authenticator.getInstance();
    	String uri = getURIRequest(request);           	
    	JSONObject jsonRequest = getJSONRequest(wrapper);        
    	
    	//Se obtiene token request por body o por parametro
    	String tokenRequest = getTokenRequest(jsonRequest);
    	if (tokenRequest==null)
    		if (parms.containsKey("token"))
    			tokenRequest = parms.get("token")[0].toString();
    	
    	//Current Session    	
    	/*Session currentSession = getCurrentSession(request);   
        String currentUsername = "";
        String currentToken = "";
        
        if (currentSession!=null){
        	
        	scope = currentSession.getScope();
        	if (currentSession.getToken()!=null){
    	        currentUsername = currentSession.getUsername();
    	        long currentExpiryTime = currentSession.getToken().getExpiryTime();    	        
    	        currentToken = authenticator.decodeToken(currentExpiryTime).getCode();
        	}
        }*/
    	
    	//Current Credentials
    	String currentToken = "";
    	String currentUserid = "";
    	if (SecurityContextHolder.getContext().getAuthentication()!=null){
    		currentToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        	currentUserid = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();    		
    	}
        
    	if (uri.equals(uAPI)){
    		
    		scope = API;
    		Login loginRequest = getLoginRequest(jsonRequest);
    		//Verify authentication and get token
    		Login login = authenticator.auth(loginRequest);
    		if (login.isConnect())
    		{
    			authorize(wrapper, login.getId(), authenticator.calculateToken().getCode());
    			
    	    	HttpSession httpSession = ((HttpServletRequest)request).getSession();  
    	    	httpSession.setAttribute("username", login.getUsername());
    	    	
    		}else
    	        SecurityContextHolder.getContext().setAuthentication(null);    	
    		
    	}else if (uri.equals(uFACEBOOK)){
    		scope = FACEBOOK;
    	}else{
    		//Se ejecuta otro metodo de la API
    		scope = API;
    		    		
    		if (tokenRequest!=null)
    			if(!tokenRequest.equals(currentToken) && SecurityContextHolder.getContext().getAuthentication()!=null)
    				//No Autorizado
    				//SecurityContextHolder.getContext().setAuthentication(null);
    				SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    		
    	}
    		
    		
    	if (scope.equals(FACEBOOK))
    		
    		authorize(wrapper, currentUserid, tokenRequest);
    	
    	else{
    		
        	if (tokenRequest!= null) {        	
        		System.out.println("Token: " + tokenRequest);                
        		if (currentToken.equals(tokenRequest)) {
        			System.out.println("valid token found");
        			authorize(wrapper, currentUserid, tokenRequest);                
        		}else{
        			System.out.println("invalid token");
                }                        	           
            } else {        	
                System.out.println("no token found");
            }
    	}
        
        // continue thru the filter chain
        //chain.doFilter(request, response);
    	chain.doFilter(wrapper, response);
    }
    
    private void authorize(ServletRequest request, String username, String credentials){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, credentials);
        token.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, credentials, authorities); //this.authenticationProvider.authenticate(token);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    

	private JSONObject getJSONRequest(ServletRequest request) throws IOException{
		JSONObject jsonRequest = new  JSONObject(); 		
	    String line = null;
	    
	    //HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);  
        StringBuilder stringBuilder = new StringBuilder();  
        BufferedReader bufferedReader = null;  
        try {  
            InputStream inputStream = request.getInputStream();  
            if (inputStream != null) {  
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
                char[] charBuffer = new char[128];  
                int bytesRead = -1;  
                while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {  
                    stringBuilder.append(charBuffer, 0, bytesRead);  
                }  
            }  
        } catch (IOException e) {  
            //log.error("Error reading the request payload", ex);  
        	System.out.println(e.toString());   
        } finally {  
            if (bufferedReader != null) {  
                try {  
                    bufferedReader.close();  
                } catch (IOException iox) {  
                    // ignore  
                }  
            }  
        }  
        	
	    if (stringBuilder.length()>0){
	    	try {
			      JSONParser jsonParser = new JSONParser();
			      jsonRequest = (JSONObject) jsonParser.parse(stringBuilder.toString());	    
			} catch (ParseException e) {
			      // crash and burn
			      throw new IOException("Error parsing JSON request string");
			}
	    }
	    
	    return jsonRequest;
	}
	
    private String getURIRequest(ServletRequest request) {
    	return ((HttpServletRequest)request).getRequestURI();
    }
    
	private Login getLoginRequest(JSONObject request) {
		Login login = new Login();
	    
		if (request!=null && request.size()>0){
			String id = request.get("userid").toString();
			String password = request.get("password").toString();			
			login.setId(id);
			login.setPassword(password);
		}
		
		return login;
	}
	
	private String getTokenRequest(JSONObject request) {
		
		if (request.size()>0 && request.containsKey("token"))			
			return request.get("token").toString();
		else
			return null;
								
	}
	
	private Session getCurrentSession(ServletRequest request){
		Session session = new Session();
		session = (Session)((HttpServletRequest)request).getSession().getAttribute("userSession");
		
		return session;
	}	

}
