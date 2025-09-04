package com.Ritik.demo.Utility;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Date;

@Component
public class JWT_Util {
    private static final String SECRET = "z3XqR5uK1Y1lkgMj84hH7W6xG9sv8lJjY2lT9D9s4ow=";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    // Generate JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Username from Token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract Claims from Token
    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired. Please login again.");
        } catch (MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token format.");
        } catch (SignatureException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token signature.");
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.");
        }
    }

    // Validate Token (Checks Expiry + Username)
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = extractClaims(token);
            return claims.getSubject().equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Check if Token is Expired
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}