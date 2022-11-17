package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.Token;
import com.greentea.surgom.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemoryTokenRepositoryTest {

    @Autowired private MemberService memberService;
    @Autowired private JWTTokenRepository tokenRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        //when
        Member member_result = memberService.save(member);

        //then
        Optional<Token> token_result = tokenRepository.findByPhone(member.getPhone());
        assertEquals(token_result.get().getPhone(), member_result.getPhone());
    }
}
