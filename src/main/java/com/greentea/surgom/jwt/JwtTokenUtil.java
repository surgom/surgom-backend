package com.greentea.surgom.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private Key secret_key;
    @Value("${jwt.token.expire-length")
    private long expire_time;

    public JwtTokenUtil(@Value("${jwt.token.secret-key") String secret_key) {
        byte[] keyBytes = Decoders.BASE64URL.decode(secret_key);;
        this.secret_key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String getPhoneFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret_key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateAccessToken(Map<String, Object> claims) {
        return doGenerateAccessToken(claims);
    }

    private String doGenerateAccessToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expire_time))
                .signWith(secret_key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> claims) {
        return doGenerateRefreshToken(claims);
    }

    private String doGenerateRefreshToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(secret_key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String phone = getPhoneFromToken(token);
        return (phone.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
