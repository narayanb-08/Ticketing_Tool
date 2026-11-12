package com.sumpositive.ticketing.security;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtSecurity {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:1800000}")
    private long jwtExpiration;

    private SecretKey secretKey;

    // Initialize secret key
    @jakarta.annotation.PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
    }

    // Generate JWT token
    public String generateToken(String email) {

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    // Extract email from JWT
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // Extract JWT claims
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check whether token is expired
    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration == null || expiration.before(new Date());
    }

    // Validate JWT token
    public boolean validateToken(String token, String email) {
        try {
            String extractedEmail = extractEmail(token);
            return email != null
                    && email.equals(extractedEmail)
                    && !isTokenExpired(token);

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }
}
