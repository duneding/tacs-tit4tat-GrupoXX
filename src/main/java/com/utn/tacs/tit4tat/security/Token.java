package com.utn.tacs.tit4tat.security;

public class Token  implements java.io.Serializable {
	
	private String code;
	private long expiryTime;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}
		
}
