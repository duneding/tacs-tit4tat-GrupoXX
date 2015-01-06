package com.utn.tacs.tit4tat.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;
 
public class AutentiaAuthenticationFailureHandler extends
        SimpleUrlAuthenticationFailureHandler {
     
    private String defaultFailureUrl;
    private String expiredUrl;
 
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        final String failureUrl=getFailureUrl(exception);
        if (failureUrl == null) {
            logger.debug("No failure URL set, sending 401 Unauthorized error");
 
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());
        } else {
            saveException(request, exception);
 
            if (isUseForward()) {
                logger.debug("Forwarding to " + failureUrl);
 
                request.getRequestDispatcher(failureUrl).forward(request, response);
            } else {
                logger.debug("Redirecting to " + failureUrl);
                getRedirectStrategy().sendRedirect(request, response, failureUrl);
            }
        }
    }
     
    @Override
    public void setDefaultFailureUrl(String defaultFailureUrl) {
       super.setDefaultFailureUrl(defaultFailureUrl);
        this.defaultFailureUrl = defaultFailureUrl;
    }
     
    private String getFailureUrl(AuthenticationException exception) {
        if(exception instanceof NonceExpiredException){
            return expiredUrl;
        }
        return defaultFailureUrl;
    }
 
    public void setExpiredUrl(String expiredUrl) {
        this.expiredUrl = expiredUrl;
    }
}