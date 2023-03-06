package com.greentea.surgom.controller;

import com.greentea.surgom.domain.*;
import com.greentea.surgom.dto.MemberDto;
import com.greentea.surgom.dto.TokenDto;
import com.greentea.surgom.jwt.JwtFilter;
import com.greentea.surgom.jwt.TokenProvider;
import com.greentea.surgom.service.MemberService;
import com.greentea.surgom.service.NaverLoginService;
import com.greentea.surgom.service.TokenService;
import com.greentea.surgom.vo.NaverLoginVo;
import com.greentea.surgom.vo.NaverProfileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MemberOauthController {

    @Autowired
    NaverLoginService naverLoginService;
    @Autowired
    MemberService memberService;
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    TokenService tokenService;

    @GetMapping(value = "/join/naver", produces = "application/json; charset=utf8")
    public  ResponseEntity naverOAuthRedirect(@RequestParam Map<String, String> resValue) {
        NaverLoginVo naverLoginVo = naverLoginService.requestNaverLoginAccessToken(resValue, "authorization_code");
        NaverProfileVo naverProfileVo = naverLoginService.requestNaverLoginProfile(naverLoginVo);

        try {
            //member
            Gender gender = naverProfileVo.getGender() == "Female" ? Gender.FEMALE : Gender.MALE;
            MemberDto memberDto = new MemberDto(
                    naverProfileVo.getMobile(),
                    naverProfileVo.getNickname(),
                    naverProfileVo.getName(),
                    Integer.parseInt(naverProfileVo.getBirthyear()),
                    gender,
                    0L, Authority.USER, "Naver"
            );

            MemberDto save_member = memberService.signUp(memberDto);

            //token
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberDto.getName(), memberDto.getPhone());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt_access = tokenProvider.createAccessToken(authentication);
            String jwt_refresh = tokenProvider.createRefreshToken(authentication);

            TokenDto tokenDto = new TokenDto(
                    save_member.getPhone(),
                    jwt_access,
                    jwt_refresh
                    );

            TokenDto save_token = tokenService.signUp(tokenDto);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + save_token.getJwtAccessToken());

            return new ResponseEntity<>(save_token.getJwtAccessToken(), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
