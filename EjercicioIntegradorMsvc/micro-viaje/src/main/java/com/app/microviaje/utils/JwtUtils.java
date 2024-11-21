package com.app.microviaje.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtils {
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    private Key generateSecretKey() {
        byte[] secretKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretKey);
    }

    public Long extractUserId(String jwt) {
        return extractAllClaims(jwt).get("userId", Long.class);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateSecretKey()).build()
                .parseClaimsJws(jwt).getBody();
    }
}
