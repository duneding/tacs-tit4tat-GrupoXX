package com.utn.tacs.tit4tat.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;

import com.utn.tacs.tit4tat.service.UsuarioService;

public class Authenticator {

	@Autowired
	private UsuarioService usuarioService;
	
	private static Authenticator instance = null;
    private String key = "KEY";    
    private long nonceValiditySeconds=10;
    private static final String NONCE_FIELD_SEPARATOR = ":";
	
    protected Authenticator() {
	      // Exists only to defeat instantiation.
	}
	   
    public static Authenticator getInstance() {
        if(instance == null) {
           instance = new Authenticator();
        }
        return instance;
    }

	@SuppressWarnings("rawtypes")
	public boolean auth(Login login) {
		
		CustomAuthenticationProvider authProvider = CustomAuthenticationProvider.getInstance();
		String passwordEncoded = authProvider.encodePassword(login.getPassword());;
		/*Iterator iterator = this.usuarioService.getUsuarios().iterator();
		
		while(iterator.hasNext()){
			Usuario user = (Usuario) iterator.next();				  
			
			if (user.getId().toString().equals(login.getPassword()) && user.getPassword().equals(passwordEncoded))						 
				return true;			
		}*/
		return true;		
	}
	
    public Token calculateToken() {
    	
        final long expiryTime = System.currentTimeMillis()
                + (nonceValiditySeconds * 1000);
        
        return codeToken(expiryTime);
    }
     
    public Token decodeToken(long expiryTime) {
    	return codeToken(expiryTime);
    }
    
    public Token codeToken(long expiryTime) {
    	
    	Token token = new Token();
    	
        final String signatureValue = md5Hex(new StringBuilder().append(expiryTime).append(NONCE_FIELD_SEPARATOR).append(key).toString());        
        final String nonceValue = new StringBuilder().append(expiryTime).append(NONCE_FIELD_SEPARATOR).append(signatureValue).toString();
        
        token.setCode(new String(Base64.encode(nonceValue.getBytes())));
        token.setExpiryTime(expiryTime);
        
        return token;
    }
    
    public String encodePassword(String value) {
    	final String signatureValue = md5Hex(new StringBuilder().append(value).append(NONCE_FIELD_SEPARATOR).append(key).toString());
        return new String(Base64.encode(signatureValue.getBytes()));
    }
    
    public static String md5Hex(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new String(Hex.encode(digest.digest(data.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
 
    }	
}
