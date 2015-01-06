package com.utn.tacs.tit4tat.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UsernamePasswordWithTimeoutAuthenticationToken extends
        UsernamePasswordAuthenticationToken {
 
    private String timeout;
 
    public UsernamePasswordWithTimeoutAuthenticationToken(Object principal,
            Object credentials) {
        super(principal, credentials);
        this.timeout=null;
    }
 
    public UsernamePasswordWithTimeoutAuthenticationToken(Object principal,
            Object credentials, String timeout) {
        this(principal, credentials);
        this.timeout=timeout;
    }
     
    public String getTimeout() {
        return timeout;
    }   
}
