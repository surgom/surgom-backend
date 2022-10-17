package com.greentea.surgom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentea.surgom.security.NaverProfile;
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

        ObjectMapper objectMapper = new ObjectMapper();

        NaverProfile naverProfile = null;
        try {
            naverProfile = objectMapper.readValue(profileResponse.getBody(), NaverProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("access_token", naverProfile.getResponse().getAccess_token());
        model.addAttribute("refresh_token", naverProfile.getResponse().getRefresh_token());
        model.addAttribute("name", naverProfile.getResponse().getName());
        model.addAttribute("nickname", naverProfile.getResponse().getNickname());
        model.addAttribute("gender", naverProfile.getResponse().getGender());
        model.addAttribute("birthyear", naverProfile.getResponse().getBirthyear());
        model.addAttribute("mobile", naverProfile.getResponse().getMobile());

        return "/login/login-success";
    }
}
