package com.greentea.surgom.security;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key secret_key;

    @Value("${jwt.token.expire-length")
    private long expire_time;

    @Autowired
    MemberService memberService;

    public JwtTokenProvider(@Value("${jwt.token.secret-key") String secret_key) {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        this.secret_key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());

        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + expire_time);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(secret_key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String phone = Jwts.parserBuilder().setSigningKey(secret_key).build().parseClaimsJws(token).getBody().getSubject();



        return new UsernamePasswordAuthenticationToken(member, "", member.getAuthority());
    }
}
