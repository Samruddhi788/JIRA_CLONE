package com.example.demo.services;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Secret key used to SIGN and VERIFY JWTs
    // Must be kept private. This is Base64-decoded for HS256 algorithm.
    private static final String SECRET_KEY =
            "c3VwZXItc2VjcmV0LWp3dC1rZXktZm9yLWhzMjU2LXNpZ25pbmc=";//trap 1 we did not have a kety for base 64

    /* ======================================================
       TOKEN GENERATION
       ====================================================== */

    // Public method: generates JWT for a given user with no extra claims
    // public String generateToken(UserDetails userDetails) {
    //     return generateToken(Map.of(), userDetails);
    // This is the updated one to generate token with roles as claims
   

    // Core token generation logic
   public String generateToken(UserDetails userDetails) {

    Map<String, Object> claims = Map.of(
        "roles",
        userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(java.util.stream.Collectors.toList())
    );

    return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
}


    /* ======================================================
       TOKEN VALIDATION
       ====================================================== */

    // Validates token: signature, username, and expiration
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        // Valid if username matches and token not expired
        // Signature is automatically verified during parsing
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    // Check if token has expired
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    /* ======================================================
       CLAIM EXTRACTION
       ====================================================== */

    // Extract username (subject) from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generic method to extract any claim using a function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse JWT, verify signature, and return claims
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                // Set signing key to verify token integrity
                .setSigningKey(getSignInKey())
                .build()
                // Parses token, throws exception if signature invalid
                .parseClaimsJws(token)
                .getBody();
    }

    /* ======================================================
       SIGNING KEY
       ====================================================== */

    // Converts Base64-encoded secret string into HMAC-SHA256 key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
