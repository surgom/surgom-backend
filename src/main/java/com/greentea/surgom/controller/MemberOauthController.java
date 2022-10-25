package com.greentea.surgom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentea.surgom.domain.Authority;
import com.greentea.surgom.domain.Gender;
import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.Token;
import com.greentea.surgom.jwt.JwtTokenUtil;
import com.greentea.surgom.repository.TokenRepository;
import com.greentea.surgom.security.NaverProfile;
import com.greentea.surgom.service.MemberService;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberOauthController {

    @Autowired
    MemberService memberService;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/naver")
    @ResponseBody
    public String naverOAuthRedirect(@RequestParam String access_token, @RequestParam String refresh_token, Model model,
                                     @Value("${spring.security.oauth2.client.registration.naver.client-id}") String client_id,
                                     @Value("${spring.security.oauth2.client.registration.naver.client-secret}") String client_secret,
                                     @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}") String authorization_grant_type) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders profileRequestHeader = new HttpHeaders();
        profileRequestHeader.add("Authorization", "Bearer " + access_token);

        HttpEntity<HttpHeaders> profileHttpEntity = new HttpEntity<>(profileRequestHeader);

        ResponseEntity<String> profileResponse = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                profileHttpEntity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();

        NaverProfile naverProfile = null;
        String token = null;
        try {
            naverProfile = objectMapper.readValue(profileResponse.getBody(), NaverProfile.class);

            Gender memberGender;
            if (naverProfile.getResponse().getGender().equals("F")) memberGender = Gender.FEMALE;
            else memberGender = Gender.MALE;

            memberService.save(new Member(naverProfile.getResponse().getMobile(), naverProfile.getResponse().getNickname(), naverProfile.getResponse().getName(), Integer.parseInt(naverProfile.getResponse().getBirthyear()), memberGender, 0L, Authority.USER, naverProfile.getResponse().getId()));

            Map<String, Object> claim = new HashMap<String, Object>();
            claim.put("phone", naverProfile.getResponse().getMobile());
            claim.put("name", naverProfile.getResponse().getName());

            String jwt_access = jwtTokenUtil.generateAccessToken(claim);
            String jwt_refresh = jwtTokenUtil.generateRefreshToken(claim);

            tokenRepository.save(new Token(naverProfile.getResponse().getMobile(), access_token, refresh_token, jwt_access, jwt_refresh));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return token;
    }
}
