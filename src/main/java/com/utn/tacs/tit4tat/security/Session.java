package com.utn.tacs.tit4tat.security;

public class Session implements java.io.Serializable {
	
	private static Session instance = null;
	
	private long userid;
	private String username;
	private Token token;
	private String scope;
	
    protected Session() {
	      // Exists only to defeat instantiation.
	}
	   
    public static Session getInstance() {
        if(instance == null) {
           instance = new Session();
        }
        return instance;
    }
    
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}			
}
