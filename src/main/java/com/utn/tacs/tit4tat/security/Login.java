package com.utn.tacs.tit4tat.security;

public class Login {
	
	private String username;
	private String id;
	private String password;
	private boolean connect;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isConnect() {
		return connect;
	}
	public void setConnect(boolean connect) {
		this.connect = connect;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
		
}
