package com.ocbc.ms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/auth/signin",
        "/auth/signup",
        "/test"
    );
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        boolean shouldSkip = PUBLIC_PATHS.stream().anyMatch(path::startsWith);
        if (shouldSkip) {
            logger.debug("Skipping filter for public endpoint: {}", path);
        }
        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();
        logger.debug("Processing request: {} {}", method, path);

        try {
            String jwt = parseJwt(request);
            logger.debug("JWT token present: {}", jwt != null);
            
            if (jwt != null) {
                logger.debug("Validating JWT token...");
                if (jwtUtils.validateJwtToken(jwt)) {
                    logger.debug("JWT token is valid");
                    String username = jwtUtils.extractUsername(jwt);
                    logger.debug("Extracted username: {}", username);

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        logger.debug("Loaded user details for: {}", username);
                        
                        if (jwtUtils.isTokenValid(jwt, userDetails)) {
                            logger.debug("Token is valid for user: {}", username);
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                            );
                            
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContext context = SecurityContextHolder.createEmptyContext();
                            context.setAuthentication(authToken);
                            SecurityContextHolder.setContext(context);
                            logger.debug("Authentication set successfully for user: {}", username);
                        } else {
                            logger.warn("Token validation failed for user: {}", username);
                        }
                    }
                } else {
                    logger.warn("JWT token validation failed");
                }
            } else {
                logger.debug("No JWT token found in request");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
