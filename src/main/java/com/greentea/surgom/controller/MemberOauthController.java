package com.greentea.surgom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentea.surgom.security.NaverOauthParams;
import lombok.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MemberOauthController {
    @GetMapping("/naver")
    public String naverOAuthRedirect(@RequestParam String code, @RequestParam String state, Model model,
                                     @Value("${spring.security.oauth2.client.registration.naver.client-id}") String client_id,
                                     @Value("${spring.security.oauth2.client.registration.naver.client-secret}") String client_secret,
                                     @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}") String authorization_grant_type) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("grant_type", authorization_grant_type);
        accessTokenParams.add("client_id", client_id);
        accessTokenParams.add("client_secret", client_secret);
        accessTokenParams.add("code" , code);
        accessTokenParams.add("state" , state);

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessTokenHeaders);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class
        );

        // json->객체 변환을 위한 ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();
        NaverOauthParams naverOauthParams = null;
        try {
            naverOauthParams = objectMapper.readValue(accessTokenResponse.getBody(), NaverOauthParams.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders profileRequestHeader = new HttpHeaders();
        profileRequestHeader.add("Authorization", "Bearer " + naverOauthParams.getAccess_token());

        HttpEntity<HttpHeaders> profileHttpEntity = new HttpEntity<>(profileRequestHeader);

        ResponseEntity<String> profileResponse = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                profileHttpEntity,
                String.class
        );

//        NaverProfile naverProfile = null;
//        try {
//            naverProfile = objectMapper.readValue(profileResponse.getBody(), NaverProfile.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        model.addAttribute("name", naverProfile.getResponse().getName());
//        model.addAttribute("image", naverProfile.getResponse().getProfile_image());
//
//        return "/login/login-success";
    }
}
