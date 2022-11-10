package com.greentea.surgom.service;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.Token;
import com.greentea.surgom.repository.MemberRepository;
import com.greentea.surgom.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TokenRepository tokenRepository;

    public Member save(Member member) {
        Member member_result = memberRepository.save(member);

        tokenRepository.save(new Token(member.getPhone(), null, null, null, null));

        return member_result;
    }

    public Optional<Member> getMember(HttpServletRequest request) {
        Object jwt_access = request.getAttribute("jwt_access");

        Optional<Token> member_token = tokenRepository.findByJwtAccess(jwt_access);
        Optional<Member> Member = memberRepository.findByPhone(member_token.get().getPhone());

        return Member;
    }
}
