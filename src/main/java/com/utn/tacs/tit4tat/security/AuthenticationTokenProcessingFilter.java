package com.utn.tacs.tit4tat.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    @SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

    	String uri = ((HttpServletRequest)request).getRequestURI();       
    	String scope = "";
    	        	
        Map<String, String[]> parms = request.getParameterMap();
        //((HttpServletRequest)request).getSession().getAttribute("userSession")
        
        Session currentSession = new Session();
        CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        
        currentSession = (Session)((HttpServletRequest)request).getSession().getAttribute("userSession");        
        String tokenParam = "";
        String tokenCalculated = "";
        String username = "";
        
        if (currentSession!=null){
        	
        	scope = currentSession.getScope();
        	if (currentSession.getToken()!=null){
    	        username = currentSession.getUsername();
    	        long expiryTime = currentSession.getToken().getExpiryTime();
    	        tokenParam = currentSession.getToken().getCode();    	        
    	        tokenCalculated = authProvider.decodeToken(expiryTime).getCode();
        	}
        }
        
        if (parms.containsKey("token") || scope.equals("facebook")) {
        	
        	if (!scope.equals("facebook")){
                String strToken = parms.get("token")[0]; // grab the first "token" parameter
                System.out.println("Token: " + strToken);
                
                if (tokenCalculated.equals(tokenParam)) {
                	System.out.println("valid token found");
                	authorize(request, username, tokenParam);
                	
                }else{
                    System.out.println("invalid token");
                }                
        	}else
        		authorize(request, username, tokenParam);
        	            
        } else if (!uri.equals("/login") && !uri.equals("/") && !uri.equals("/user")){
        	
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

}
