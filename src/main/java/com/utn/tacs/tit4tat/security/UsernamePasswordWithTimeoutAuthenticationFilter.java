package com.utn.tacs.tit4tat.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
public class UsernamePasswordWithTimeoutAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {
 
    private String timeoutParameter = "timeout";
    private boolean postOnly;
 
    @Override
    public void setPostOnly(boolean postOnly) {
        super.setPostOnly(postOnly);
        this.postOnly = postOnly;
    }
 
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: "
                            + request.getMethod());
        }
 
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        final String timeout = obtainTimeout(request);
 
        if (username == null) {
            username = "";
        }
 
        if (password == null) {
            password = "";
        }
 
        username = username.trim();
 
        final UsernamePasswordWithTimeoutAuthenticationToken authRequest = new UsernamePasswordWithTimeoutAuthenticationToken(
                username, password, timeout);
 
        setDetails(request, authRequest);
 
        return this.getAuthenticationManager().authenticate(authRequest);
    }
     
    protected String obtainTimeout(HttpServletRequest request) {
        return request.getParameter(timeoutParameter);
    }
 
    public void setTimeoutParameter(String timeoutParameter) {
        this.timeoutParameter = timeoutParameter;
    }
}