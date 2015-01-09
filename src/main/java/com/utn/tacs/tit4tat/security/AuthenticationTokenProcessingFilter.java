package com.utn.tacs.tit4tat.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

import com.utn.tacs.tit4tat.service.UsuarioService;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
	
	private final String API = "api";
	private final String FACEBOOK = "facebook";
	private final String uAPI = "/login";
	private final String uFACEBOOK = "/user";

    @SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
    	String scope = "";
    	Authenticator authenticator = Authenticator.getInstance();
    	String uri = getURIRequest(request);           	
    	JSONObject jsonRequest = getJSONRequest(request);        
    	String tokenRequest = getTokenRequest(jsonRequest);
    	
    	//Current Session    	
    	Session currentSession = getCurrentSession(request);   
        String currentUsername = "";
        String currentToken = "";
        
        if (currentSession!=null){
        	
        	scope = currentSession.getScope();
        	if (currentSession.getToken()!=null){
    	        currentUsername = currentSession.getUsername();
    	        long currentExpiryTime = currentSession.getToken().getExpiryTime();
    	        //currentToken = currentSession.getToken().getCode();    	        
    	        currentToken = authenticator.decodeToken(currentExpiryTime).getCode();
        	}
        }
    	
    	if (uri.equals(uAPI)){
    		
    		scope = API;
    		Login login = getLoginRequest(jsonRequest);
    		//Verify authentication and get token
    		if (authenticator.auth(login))
    			authorize(request, login.getId(), login.getPassword());
    		
    	}else if (uri.equals(uFACEBOOK)){
    		scope = FACEBOOK;
    	}
    		
    	if (scope.equals(FACEBOOK))
    		authorize(request, currentUsername, tokenRequest);
    	else{
    		
    	}
        
    	if (tokenRequest!= null) {        	
    		System.out.println("Token: " + tokenRequest);                
    		if (currentToken.equals(tokenRequest)) {
    			System.out.println("valid token found");
    			authorize(request, currentUsername, tokenRequest);                
    		}else{
    			System.out.println("invalid token");
            }                        	           
        } else {        	
            System.out.println("no token found");
        }

        // continue thru the filter chain
        chain.doFilter(request, response);
    }
    
    private void authorize(ServletRequest request, String username, String password){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        token.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorities); //this.authenticationProvider.authenticate(token);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    

    
    private String getURIRequest(ServletRequest request) {
    	return ((HttpServletRequest)request).getRequestURI();
    }
    
	private JSONObject getJSONRequest(ServletRequest request) throws IOException{
		JSONObject jsonRequest = new  JSONObject(); 
		StringBuffer jb = new StringBuffer();
	    String line = null;
	    /*try {
	      BufferedReader reader = request.getReader();
	      while ((line = reader.readLine()) != null)
	        jb.append(line);
	    } catch (Exception e) { //TODO }
	*/
	
	    if (jb.length()>0){
		    try {
			      JSONParser jsonParser = new JSONParser();
			      jsonRequest = (JSONObject) jsonParser.parse(jb.toString());	    
			} catch (ParseException e) {
			      // crash and burn
			      throw new IOException("Error parsing JSON request string");
			}
	    }
	    
	    return jsonRequest;
	}
	
	private Login getLoginRequest(JSONObject request) {
		Login login = new Login();
	    
		String id = request.get("userid").toString();
		String password = request.get("password").toString();			
		login.setId(id);
		login.setPassword(password);
		
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
