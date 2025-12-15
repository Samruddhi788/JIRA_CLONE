package com.example.demo.services;
import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
    // Implementation of JWT Service

    private static final String SECRET_KEY="33e2e10a559851182647f1fbe9245b1228a343c7fe9557d7067c4a4349f7cfde";

    public String extractUsername(String Token){
        return extractAllClaims(Token).getSubject();

    }
    private Key getSignInKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
      return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
    public <T>T extractClaim(String token, java.util.function.Function<Claims,T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}