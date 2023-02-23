package com.greentea.surgom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentea.surgom.domain.*;
import com.greentea.surgom.dto.MemberDto;
import com.greentea.surgom.dto.TokenDto;
import com.greentea.surgom.jwt.TokenProvider;
import com.greentea.surgom.repository.JwtTokenRepository;
import com.greentea.surgom.service.MemberService;
import com.greentea.surgom.service.NaverLoginService;
import com.greentea.surgom.service.TokenService;
import com.greentea.surgom.vo.NaverLoginVo;
import com.greentea.surgom.vo.NaverProfileVo;
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
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/join/naver")
    public  ResponseEntity naverOAuthRedirect(@RequestParam Map<String, String> resValue) {
        NaverLoginVo naverLoginVo = naverLoginService.requestNaverLoginAccessToken(resValue, "authorization_code");
        NaverProfileVo naverProfileVo = naverLoginService.requestNaverLoginProfile(naverLoginVo);

        Gender gender = naverProfileVo.getGender() == "Female" ? Gender.FEMALE : Gender.MALE;
        MemberDto memberDto = new MemberDto(
                naverProfileVo.getMobile(),
                naverProfileVo.getNickname(),
                naverProfileVo.getName(),
                Integer.parseInt(naverProfileVo.getBirthyear()),
                gender,
                0L, Authority.USER, "Naver"
                );

//        try {
//            memberService.isMember(naverProfileVo.getMobile());
//
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(naverProfileVo.getMobile(), naverProfileVo.getName());
//
//            //authenticate시 CustomMemberDetailsService의 loadbyusername실행
//            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            String jwt_access = tokenProvider.createAccessToken(authentication);
//            String jwt_refresh = tokenProvider.createRefreshToken(authentication);
//
//            TokenDto tokenDto = new TokenDto(
//                    naverProfileVo.getMobile(),
//                    jwt_access,
//                    jwt_refresh
//                    );
//
//            memberService.signUp(memberDto, tokenDto);
//            return ResponseEntity.ok(tokenDto);
//        } catch (Exception e) {
//            return ResponseEntity.ok(e.getMessage());
//        }
        return ResponseEntity.ok(memberDto);
    }
}
