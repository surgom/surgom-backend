package com.greentea.surgom.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUserDetailsService jwtUserDetailsService;
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String phone = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.split(" ")[1].trim();
            try {
                phone = jwtTokenUtil.getPhoneFromToken(jwtToken);
            } catch (IllegalStateException e) {
                System.out.println("JWT Token을 가져올 수 없습니다");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token이 만료되었습니다");
            }
        }
        else
            logger.warn("JWT Token의 형식이 잘못 되었습니다");

        request.setAttribute("access_token", jwtToken);
        filterChain.doFilter(request, response);
    }
}
