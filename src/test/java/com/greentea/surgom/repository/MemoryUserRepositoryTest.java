package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemoryUserRepositoryTest {

    @Autowired MemoryMemberRepository memoryMemberRepository;
    @Autowired MemberService memberService;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        String phone = memberService.join(member);

        em.flush();
        assertEquals(member, memberService.findOne(member.getPhone()));
    }
}