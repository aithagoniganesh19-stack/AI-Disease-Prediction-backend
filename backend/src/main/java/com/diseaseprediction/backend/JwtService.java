package com.diseaseprediction.backend;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    // Injected from application.properties / Render env var JWT_SECRET.
    // Use a long random string (≥ 32 chars) in production.
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + (1000L * 60 * 60); // 1 hour

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(currentTime))
                .expiration(new Date(expirationTime))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token, String email) {
        try {
            String tokenEmail = extractEmail(token);
            return tokenEmail.equals(email);
        } catch (Exception exception) {
            return false;
        }
    }
}
