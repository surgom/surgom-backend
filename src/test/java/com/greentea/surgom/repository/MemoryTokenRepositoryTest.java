package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Token;
import com.greentea.surgom.dto.MemberDto;
import com.greentea.surgom.service.MemberService;
import org.junit.jupiter.api.Test;
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
    @Autowired private TokenRepository tokenRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        MemberDto member = MemberDto.builder()
                .name("박성하")
                .phone("010-4172-8563")
                .build();

        //when
        MemberDto member_result = memberService.signUp(member);

        //then
        Optional<Token> token_result = tokenRepository.findByPhone(member.getPhone());
        assertEquals(token_result.get().getPhone(), member_result.getPhone());
    }
}
