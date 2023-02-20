package com.greentea.surgom.service;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.Token;
import com.greentea.surgom.dto.MemberDto;
import com.greentea.surgom.exception.DuplicateMemberException;
import com.greentea.surgom.repository.MemberRepository;
import com.greentea.surgom.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Member save(Member member) {
        Member member_result = memberRepository.save(member);

        tokenRepository.save(new Token(member.getPhone(), null, null, null, null));

        return member_result;
    }

    @Transactional
    public MemberDto signUp(MemberDto memberDto) {
        if (memberRepository.findByPhone(memberDto.getPhone()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Member member = Member.builder()
                .phone(memberDto.getPhone())
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

    public Optional<Member> getMember(HttpServletRequest request) {
        Object jwt_access = request.getAttribute("jwt_access");

        Optional<Token> member_token = tokenRepository.findByJwtAccessToken(jwt_access);
        Optional<Member> Member = memberRepository.findByPhone(member_token.get().getPhone());

        return Member;
    }
}
