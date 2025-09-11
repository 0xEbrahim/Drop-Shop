package com.ibrahim.drop_shop.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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

    public String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiresIn))
                .and()
                .signWith(getAccessSecretKey())
                .compact();
    }

    public String generateRefreshToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshExpiresIn))
                .and()
                .signWith(getRefreshSecretKey())
                .compact();
    }

    private Key getRefreshSecretKey() {
        byte[] decode = Decoders.BASE64.decode(refreshSecret);
        return Keys.hmacShaKeyFor(decode);
    }

    private Key getAccessSecretKey() {
        byte[] decode = Decoders.BASE64.decode(accessSecret);
        return Keys.hmacShaKeyFor(decode);
    }


}
