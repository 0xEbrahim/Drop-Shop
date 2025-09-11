package com.ibrahim.drop_shop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${jwt.access.expiresIn}")
    private Long accessExpiresIn;

    @Value("${jwt.refresh.expiresIn}")
    private Long refreshExpiresIn;

    public String generateAccessToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiresIn))
                .and()
                .signWith(getAccessSecretKey())
                .compact();
    }

    public String generateRefreshToken(String email){
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiresIn))
                .and()
                .signWith(getRefreshSecretKey())
                .compact();
    }

    private SecretKey getRefreshSecretKey() {
        byte[] decode = Decoders.BASE64.decode(refreshSecret);
        return Keys.hmacShaKeyFor(decode);
    }

    private SecretKey getAccessSecretKey() {
        byte[] decode = Decoders.BASE64.decode(accessSecret);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> provider){
        Claims claims = extractClaims(token);
        return provider.apply(claims);
    }

    public Claims extractClaims(String token) {
        return Jwts.
                parser()
                .verifyWith(getAccessSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidToken(String token, CustomUserDetails userDetails) {
        return (Objects.equals(extractEmail(token), userDetails.getUsername()) && isNotExpired(token));
    }

    private boolean isNotExpired(String token) {
        return extractClaims(token, Claims::getExpiration).after(new Date());
    }
}
