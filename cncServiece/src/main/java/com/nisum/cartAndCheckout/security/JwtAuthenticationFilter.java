package com.nisum.cartAndCheckout.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = getTokenFromRequest(request);

            if (StringUtils.hasText(token) && jwtUtil.validateJwtToken(token)) {
                Integer userId = jwtUtil.getUserIdFromToken(token);
                jwtUtil.setUserId(userId); // ✅ Store userId for later use
                logger.info("Extracted userId: {}", userId);
            } else {
                jwtUtil.setUserId(null); // ✅ Token missing or invalid
                logger.info("No valid token. userId set to null");
            }
        } catch (Exception ex) {
            jwtUtil.setUserId(null);
            logger.warn("Error parsing token: {}", ex.getMessage());
        }

        filterChain.doFilter(request, response); // ✅ Let request continue
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}