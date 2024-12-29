package org.lessons.java.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
    	//Ritorna una collezione dei ruoli o authorities associati all'utente autenticato.Ogni ruolo è rappresentato come un oggetto GrantedAuthority
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String redirectUrl = "";

        //Per ogni ruolo (GrantedAuthority), verifica se è:ROLE_ADMIN reindirizza a /tickets/admin/dashboard ROLE_OPERATORE reindirizza a /tickets/operators/dashboard

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                redirectUrl = "/tickets/admin/dashboard";
                break;
            } else if (authority.getAuthority().equals("ROLE_OPERATORE")) {
                redirectUrl = "/tickets/operators/dashboard";
                break;
            }
        }

        if (redirectUrl.isEmpty()) {
            throw new IllegalStateException("Ruolo non trovato!");
        }

        response.sendRedirect(redirectUrl);
    }
}