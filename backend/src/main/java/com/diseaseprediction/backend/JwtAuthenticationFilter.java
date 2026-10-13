package com.diseaseprediction.backend;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


private final JwtService jwtService;

public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
}

@Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
) throws ServletException, IOException {

    String authorizationHeader =
            request.getHeader("Authorization");

    // Continue normally when no token is provided.
    // Spring Security will decide whether authentication is required.
    if (authorizationHeader == null
            || !authorizationHeader.startsWith("Bearer ")) {

        filterChain.doFilter(request, response);
        return;
    }

    String token = authorizationHeader.substring(7);

    try {
        String email = jwtService.extractEmail(token);

        if (email != null
                && SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

            if (jwtService.isTokenValid(token, email)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                User.withUsername(email)
                                        .password("")
                                        .authorities(
                                                Collections.emptyList()
                                        )
                                        .build(),
                                null,
                                Collections.emptyList()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

    } catch (Exception exception) {
        // An invalid or expired token is not authenticated.
        SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);
}


}
