package com.utn.tacs.tit4tat.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
 
@Service
public class AutentiaAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
 
    @Override
    public final void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        //lógica de tratamiento de autenticación correcta
        response.sendRedirect(response.encodeRedirectURL("welcome.xhtml"));
    }
}
