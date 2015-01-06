package com.utn.tacs.tit4tat.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Service;
 
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
     
    private static final Logger Log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
 
    private static final String NONCE_FIELD_SEPARATOR = ":";
 
    private String key = "KEY";
     
    private long nonceValiditySeconds=10;
 
     
    protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
 
    @Override
    public final Authentication authenticate(Authentication authentication) {
        final UsernamePasswordWithTimeoutAuthenticationToken authenticationToken = (UsernamePasswordWithTimeoutAuthenticationToken)authentication;
        validateTimeout(authenticationToken);
        //lógica de comprobación de usuario y contraseńa
        return createSuccessAuthentication(authenticationToken);
    }
     
    @Override
    public final boolean supports(Class<?> authentication) {
        return UsernamePasswordWithTimeoutAuthenticationToken.class.isAssignableFrom(authentication);
    }
     
    public long getNonceValiditySeconds() {
        return nonceValiditySeconds;
    }
 
    public void setNonceValiditySeconds(long nonceValiditySeconds) {
        this.nonceValiditySeconds = nonceValiditySeconds;
    }
     
    public String getKey() {
        return key;
    }
 
    public void setKey(String key) {
        this.key = key;
    }
 
    private void validateTimeout(
            UsernamePasswordWithTimeoutAuthenticationToken authenticationToken) {
        if(StringUtils.isEmpty( authenticationToken.getTimeout())){
            final String msg="Timeout signature not present.";
            Log.error(msg);
            throw new BadCredentialsException(msg);
        }
        final long timeOutTime=extractNonceValue(authenticationToken.getTimeout());
         
        if (isNonceExpired(timeOutTime)){
            final String msg="Login timeout";
            Log.error(msg);
            throw new NonceExpiredException(msg);
        }
    }
     
    boolean isNonceExpired(final long timeoutTime) {
        final long now = System.currentTimeMillis();
        return timeoutTime < now;
    }
     
    private long extractNonceValue(final String nonce) {
        // Check nonce was Base64 encoded (as sent by the filter)
        if (!Base64.isBase64(nonce.getBytes())) {
            throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceEncoding",
                       new Object[]{nonce}, "Nonce is not encoded in Base64; received nonce {0}"));
        }
 
        // Decode nonce from Base64
        // format of nonce is:
        // base64(expirationTime + ":" + md5Hex(expirationTime + ":" + key))
        final String nonceAsPlainText = new String(Base64.decode(nonce.getBytes()));
        final String[] nonceTokens = org.springframework.util.StringUtils.delimitedListToStringArray(nonceAsPlainText, NONCE_FIELD_SEPARATOR);
 
        if (nonceTokens.length != 2) {
            throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceNotTwoTokens",
                            new Object[]{nonceAsPlainText}, "Nonce should have yielded two tokens but was {0}"));
        }
 
        // Extract expiry time from nonce
        long nonceExpiryTime;
        try {
            nonceExpiryTime = Long.valueOf(nonceTokens[0]);
        } catch (NumberFormatException nfe) {
            throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceNotNumeric",
                            new Object[]{nonceAsPlainText},
                            "Nonce token should have yielded a numeric first token, but was {0}"),nfe);
        }
 
        // Check signature of nonce matches this expiry time
        final String expectedNonceSignature = md5Hex(nonceExpiryTime + NONCE_FIELD_SEPARATOR + key);
 
        if (!expectedNonceSignature.equals(nonceTokens[1])) {
           throw new BadCredentialsException(messages.getMessage("DigestAuthenticationFilter.nonceCompromised",
                            new Object[]{nonceAsPlainText}, "Nonce token compromised {0}"));
        }
         
        return nonceExpiryTime;
    }
 
    private Authentication createSuccessAuthentication(UsernamePasswordAuthenticationToken authenticationToken) {
        final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        //lógica de asignación de roles en authorities
        return new UsernamePasswordAuthenticationToken(authenticationToken.getPrincipal(),
                authenticationToken.getCredentials(), authorities);
    }
     
    public String calculateNonce() {
        final long expiryTime = System.currentTimeMillis()
                + (nonceValiditySeconds * 1000);
        final String signatureValue = md5Hex(new StringBuilder().append(expiryTime).append(NONCE_FIELD_SEPARATOR).append(key).toString());
        final String nonceValue = new StringBuilder().append(expiryTime).append(NONCE_FIELD_SEPARATOR).append(signatureValue).toString();
        return new String(Base64.encode(nonceValue.getBytes()));
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