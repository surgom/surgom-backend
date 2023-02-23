package com.greentea.surgom.service;

import com.greentea.surgom.vo.NaverProfileResponseVo;
import com.greentea.surgom.vo.NaverProfileVo;
import com.greentea.surgom.vo.NaverLoginVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class NaverLoginService {
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    String client_id;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    String client_secret;

    public NaverLoginVo requestNaverLoginAccessToken(Map<String, String> resValue, String grant_type){
        final String uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .queryParam("grant_type", grant_type)
                .queryParam("client_id", this.client_id)
                .queryParam("client_secret", this.client_secret)
                .queryParam("code", resValue.get("code"))
                .queryParam("state", resValue.get("state"))
                .queryParam("refresh_token", resValue.get("refresh_token")) // Access_token 갱신시 사용
                .build()
                .encode()
                .toUriString();

        return WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(NaverLoginVo.class)
                .block();
    }

    public NaverProfileVo requestNaverLoginProfile(NaverLoginVo naverLoginVo){
        final String profileUri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .build()
                .encode()
                .toUriString();

        return WebClient.create()
                .get()
                .uri(profileUri)
                .header("Authorization", "Bearer " + naverLoginVo.getAccess_token())
                .retrieve()
                .bodyToMono(NaverProfileResponseVo.class)
                .block()
                .getResponse(); // NaverLoginProfile 은 건네준다.
    }
}
