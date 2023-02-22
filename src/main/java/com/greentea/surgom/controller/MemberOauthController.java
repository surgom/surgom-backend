package com.greentea.surgom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentea.surgom.domain.*;
import com.greentea.surgom.dto.TokenDto;
import com.greentea.surgom.jwt.TokenProvider;
import com.greentea.surgom.repository.JwtTokenRepository;
import com.greentea.surgom.security.NaverProfile;
import com.greentea.surgom.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@RestController
public class MemberOauthController {

    @Autowired
    MemberService memberService;
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    JwtTokenRepository tokenRepository;
    @Autowired
    TokenProvider tokenProvider;

    @GetMapping("/join/naver")
    public  ResponseEntity naverOAuthRedirect(@RequestParam String code,
                                       @Value("${spring.security.oauth2.client.registration.naver.client-id}") String client_id,
                                       @Value("${spring.security.oauth2.client.registration.naver.client-secret}") String client_secret,
                                       @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}") String authorization_grant_type) {


//        RestTemplate rt = new RestTemplate();
//
//        HttpHeaders profileRequestHeader = new HttpHeaders();
//        profileRequestHeader.add("Authorization", "Bearer " + access_token);
//
//        HttpEntity<HttpHeaders> profileHttpEntity = new HttpEntity<>(profileRequestHeader);
//
//        ResponseEntity<String> profileResponse = rt.exchange(
//                "https://openapi.naver.com/v1/nid/me",
//                HttpMethod.POST,
//                profileHttpEntity,
//                String.class
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        NaverProfile naverProfile = null;
//        try {
//            naverProfile = objectMapper.readValue(profileResponse.getBody(), NaverProfile.class);
//
//            Gender memberGender;
//            if (naverProfile.getResponse().getGender().equals("F")) memberGender = Gender.FEMALE;
//            else memberGender = Gender.MALE;
//
//            memberService.save(new Member(naverProfile.getResponse().getMobile(), naverProfile.getResponse().getNickname(), naverProfile.getResponse().getName(), Integer.parseInt(naverProfile.getResponse().getBirthyear()), memberGender, 0L, Authority.USER, naverProfile.getResponse().getId()));
//
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(naverProfile.getResponse().getMobile(), naverProfile.getResponse().getName());
//
//            //authenticate시 CustomUserDetailsService의 loadbyusername실행
//            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            String jwt_access = tokenProvider.createAccessToken(authentication);
//            String jwt_refresh = tokenProvider.createRefreshToken(authentication);
//
//            tokenRepository.save(new Token(naverProfile.getResponse().getMobile(), access_token, refresh_token, jwt_access, jwt_refresh));
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.add("Authorization", jwt_access);
//            return ResponseEntity.ok().headers(httpHeaders).body("success");
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body("fail");
//        }
        return null;
    }
}
