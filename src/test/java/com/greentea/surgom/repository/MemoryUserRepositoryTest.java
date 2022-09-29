package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Authority;
import com.greentea.surgom.domain.Gender;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemoryUserRepositoryTest {

    @Autowired MemoryMemberRepository memoryMemberRepository;
    @Autowired MemberService memberService;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        //when
        String phone = memberService.join(member);


        //then
        em.flush();
        assertEquals(member, memberService.findOne(member.getPhone()));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원예외() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        Member member2 = new Member();
        member2.setName("한보은");
        member2.setPhone("010-4172-8563");

        //when
        String phone = memberService.join(member);
        String phone2 = memberService.join(member2);

        //then
        fail("예외가 발생했습니다.");
    }

    @Test
    public void 회원탈퇴() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        //when
        String phone = memberService.join(member);
        memberService.withdraw(member);

        //then
        assertEquals(memberService.findOne(phone), null);
    }

    @Test
    public void 회원_모두_삭제() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        Member member2 = new Member();
        member2.setName("한보은");
        member2.setPhone("010-1234-5678");

        //when
        String phone = memberService.join(member);
        String phone2 = memberService.join(member2);
        memberService.withdrawAll();

        //then
        assertEquals(memberService.findAll().size(), 0);
    }

    @Test
    public void 개인정보_변경() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        //when
        String phone = memberService.join(member);
        member.setPhone("010-1234-5678");
        memberService.update(member);

        //then
        assertEquals(memberService.findOne(phone), null);
        assertEquals(memberService.findOne("010-1234-5678").getName(), "박성하");
    }

    @Test
    public void 연령대로_회원_찾기() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");
        member.setAge(23);

        Member member2 = new Member();
        member2.setName("한보은");
        member2.setPhone("010-1234-5678");
        member2.setAge(100);

        Member member3 = new Member();
        member3.setName("한보은");
        member3.setPhone("010-9876-5432");
        member3.setAge(23);

        //when
        String phone = memberService.join(member);
        em.flush();
        String phone2 = memberService.join(member2);
        em.flush();
        String phone3 = memberService.join(member3);
        em.flush();

        //then
        List<Member> list = memberService.findAll(20, 29);
        assertEquals(list.size(), 2);

        List<Member> list2 = memberService.findAll(100, 109);
        assertEquals(list2.size(), 1);
        assertEquals(list2.get(0).getName(), "한보은");
    }

    @Test
    public void 성별로_회원_찾기() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");
        member.setGender(Gender.FEMALE);

        Member member2 = new Member();
        member2.setName("박성하");
        member2.setPhone("010-1234-5678");
        member2.setGender(Gender.MALE);

        Member member3 = new Member();
        member3.setName("한보은");
        member3.setPhone("010-9876-5432");
        member3.setGender(Gender.FEMALE);

        //when
        String phone = memberService.join(member);
        em.flush();
        String phone2 = memberService.join(member2);
        em.flush();
        String phone3 = memberService.join(member3);
        em.flush();

        //then
        List<Member> female = memberService.findALl("FEMALE");
        assertEquals(female.size(), 2);

        List<Member> male = memberService.findALl("MALE");
        assertEquals(male.size(), 1);
        assertEquals(male.get(0).getPhone(), "010-1234-5678");
    }

    @Test
    public void 연령대와_성별로_사람_찾기() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");
        member.setAge(23);
        member.setGender(Gender.FEMALE);

        Member member2 = new Member();
        member2.setName("박성하");
        member2.setPhone("010-1234-5678");
        member2.setAge(23);
        member2.setGender(Gender.MALE);

        Member member3 = new Member();
        member3.setName("한보은");
        member3.setPhone("010-9876-5432");
        member3.setAge(100);
        member3.setGender(Gender.FEMALE);

        //when
        String phone = memberService.join(member);
        em.flush();
        String phone2 = memberService.join(member2);
        em.flush();
        String phone3 = memberService.join(member3);
        em.flush();

        //then
        List<Member> list = memberService.findAll(20, 29, "FEMALE");
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getPhone(), "010-4172-8563");

        List<Member> list2 = memberService.findAll(20, 29, "MALE");
        assertEquals(list2.size(), 1);
        assertEquals(list2.get(0).getPhone(), "010-1234-5678");
    }

    @Test
    public void 권한_검사() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");
        member.setAuthority(Authority.ADMIN);

        Member member2 = new Member();
        member2.setName("한보은");
        member2.setPhone("010-1234-5678");
        member2.setAuthority(Authority.USER);

        //when
        String phone = memberService.join(member);
        em.flush();
        String phone2 = memberService.join(member2);
        em.flush();

        //then
        assertEquals(memberService.findOne("010-4172-8563").getAuthority(), Authority.ADMIN);
        assertEquals(memberService.findOne("010-1234-5678").getAuthority(), Authority.USER);
    }
}