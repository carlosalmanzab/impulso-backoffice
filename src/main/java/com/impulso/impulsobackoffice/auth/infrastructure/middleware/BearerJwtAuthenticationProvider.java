package com.impulso.impulsobackoffice.auth.infrastructure.middleware;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.impulso.impulsobackoffice.auth.domain.model.Token;
import com.impulso.impulsobackoffice.auth.domain.ports.in.ValidateAuthenticationTokenUseCasePort;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BearerJwtAuthenticationProvider extends OncePerRequestFilter {
    private final ValidateAuthenticationTokenUseCasePort validateAuthenticationToken;

    public BearerJwtAuthenticationProvider(ValidateAuthenticationTokenUseCasePort validateAuthenticationToken) {
        this.validateAuthenticationToken = validateAuthenticationToken;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String HEADER = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (HEADER == null) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if (HEADER != null && HEADER.startsWith("Bearer ")) {

            final String token = HEADER.substring(7);

            try {
                final String username = validateAuthenticationToken
                        .validToken(
                                new Token(token))
                        .getCorreoElectronico();
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()));
            } catch (final RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
            filterChain.doFilter(request, response);
        }
    }

}
