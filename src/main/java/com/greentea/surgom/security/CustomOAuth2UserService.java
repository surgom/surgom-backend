//package com.greentea.surgom.security;
//
//import com.greentea.surgom.domain.Member;
//import com.greentea.surgom.domain.Token;
//import com.greentea.surgom.repository.JwtTokenRepository;
//import com.greentea.surgom.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpSession;
//import java.util.Collections;
//
//@Service
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    @Autowired private MemberService memberService;
//    @Autowired private JwtTokenRepository tokenRepository;
//    @Autowired private HttpSession httpSession;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);
//
//        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
//
//        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
//
//        Member member = memberService.save(attributes.toMemberEntity(registrationId));
//        Token token = tokenRepository.save(attributes.toTokenEntity());
//        httpSession.setAttribute("member", new SessionMember(member));
//        httpSession.setAttribute("token", new SessionMember(token));
//
//        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
//                , attributes.getAttributes()
//                , attributes.getNameAttributeKey());
//    }
//}
