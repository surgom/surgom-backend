package com.greentea.surgom.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class MemberOauthController {
    @GetMapping("/naver")
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
