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
        return buildToken(claims, getAccessSecretKey(), email, accessExpiresIn);
    }

    public String generateRefreshToken(String email){
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh-token");
        return buildToken(claims,getRefreshSecretKey(), email, refreshExpiresIn);
    }

    public boolean isRefreshToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getRefreshSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("type").equals("refresh-token");
    }

    private String buildToken(Map<String, Object> claims, SecretKey key, String email, Long expire) {
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expire))
                .and()
                .signWith(key)
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

    public String extractAccessEmail(String token) {
        return extractClaims(token, Claims::getSubject, getAccessSecretKey());
    }

    public String extractRefreshEmail(String token){
        return extractClaims(token, Claims::getSubject, getRefreshSecretKey());
    }

    public <T> T extractClaims(String token, Function<Claims, T> provider, SecretKey secret){
        Claims claims = extractClaims(token, secret);
        return provider.apply(claims);
    }

    public Claims extractClaims(String token, SecretKey secretKey) {
        return Jwts.
                parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidAccessToken(String token, CustomUserDetails userDetails) {
        return (Objects.equals(extractAccessEmail(token), userDetails.getUsername())
                && isNotExpired(token, getAccessSecretKey()));
    }

    public boolean isValidRefreshToken(String token, CustomUserDetails userDetails){
        return (Objects.equals(extractAccessEmail(token), userDetails.getUsername())
                && isNotExpired(token, getRefreshSecretKey()));
    }

    private boolean isNotExpired(String token, SecretKey secretKey) {
        return extractClaims(token, Claims::getExpiration, secretKey).after(new Date());
    }
}
