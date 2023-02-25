package com.greentea.surgom.service;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.Token;
import com.greentea.surgom.dto.MemberDto;
import com.greentea.surgom.dto.TokenDto;
import com.greentea.surgom.exception.DuplicateMemberException;
import com.greentea.surgom.repository.MemberRepository;
import com.greentea.surgom.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtTokenRepository tokenRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Token> isMember(String phone) {
        if (memberRepository.findByPhone(phone).orElse(null) != null)
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        return tokenService.getToken(phone);
    }

    @Transactional
    public MemberDto signUp(MemberDto memberDto) {

        Member member = Member.builder()
                .phone(passwordEncoder.encode(memberDto.getPhone()))
                .nickname(memberDto.getNickname())
                .name(memberDto.getName())
                .age(memberDto.getAge())
                .gender(memberDto.getGender())
                .point(memberDto.getPoint())
                .authority(memberDto.getAuthority())
                .identifier(memberDto.getIdentifier())
                .build();

        return MemberDto.from(memberRepository.save(member));
    }

    @Transactional
    public void addJwtToken(TokenDto tokenDto) {
        Optional<Member> member = memberRepository.findByPhone(tokenDto.getPhone());

        Token token = Token.builder()
                .phone(tokenDto.getPhone())
                .jwtAccessToken(tokenDto.getJwtAccessToken())
                .jwtRefreshToken(tokenDto.getJwtRefreshToken())
                .build();

        member.get().setToken(token);
    }

    public Optional<Member> getMember(String jwt_access_token) {
        Optional<Token> member_token = tokenRepository.findByJwtAccessToken(jwt_access_token);
        Optional<Member> Member = memberRepository.findByPhone(member_token.get().getPhone());

        return Member;
    }
}
