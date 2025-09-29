package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        final long tokenExpiration = 300; //5 min
        return generateToken(user, tokenExpiration);

    }

    public String generateRefreshToken(User user) {
        final long tokenExpiration = 604800; //7d
        return generateToken(user, tokenExpiration);

    }

    private String generateToken(User user, long tokenExpiration) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("userName", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    public boolean validateToken(String token){
        try{
            var claims = getClaims(token);

            return claims.getExpiration().after(new Date());
        }
        catch (Exception e) {
            return false;
        }
    }



    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }

}
