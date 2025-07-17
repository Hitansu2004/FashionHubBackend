package com.nisum.cartAndCheckout.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 1 day
    private long jwtExpirationMs;

    private Integer userId = null;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // ✅ Updated to include userId
    public String generateToken(String username, Integer userId, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId) // ✅ Include userId in payload
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Error extracting username from JWT token: {}", e.getMessage());
            return null;
        }
    }

    public List<String> getRolesFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                    .parseClaimsJws(token).getBody();
            Object rolesObj = claims.get("roles");
            if (rolesObj instanceof List<?>) {
                return ((List<?>) rolesObj).stream().map(Object::toString).collect(Collectors.toList());
            }
            return java.util.Collections.emptyList();
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Error extracting roles from JWT token: {}", e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("JWT token validation failed: {}", e.getMessage());
            return false;
        }
    }

    // ✅ Safe extraction of userId (returns null instead of exception)
    public Integer getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                    .parseClaimsJws(token).getBody();
            Object userIdObj = claims.get("userId");
            if (userIdObj != null) {
                return Integer.valueOf(userIdObj.toString());
            }
            logger.warn("JWT does not contain userId claim");
            return null;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Error extracting userId from JWT token: {}", e.getMessage());
            return null;
        }
    }

    // ✅ Static userId storage (temporary, per-request)
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserIdFromUtil() {
        return this.userId;
    }
}