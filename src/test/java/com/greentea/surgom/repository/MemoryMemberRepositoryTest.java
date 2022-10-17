package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Authority;
import com.greentea.surgom.domain.Gender;
import com.greentea.surgom.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemoryMemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        //when
        Member member_result = memberRepository.save(member);

        //then
        assertEquals(member.getPhone(), member_result.getPhone());
    }

    @Test(expected = AssertionError.class)
    public void 중복회원예외() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        Member member2 = new Member();
        member2.setName("한보은");
        member2.setPhone("010-4172-8563");

        //when
        memberRepository.save(member);
        memberRepository.save(member2);

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
        Member member_result = memberRepository.save(member);
        memberRepository.delete(member_result);

        //then
        assertEquals(memberRepository.findByPhone(member.getPhone()), Optional.empty());
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
        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.deleteAllInBatch();

        //then
        assertEquals(memberRepository.findAll().size(), 0);
    }

    @Test
    public void 전화번호_제외_개인정보_변경() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");

        //when
        Member member_result = memberRepository.save(member);
        member.setName("한보은");
        memberRepository.save(member);

        //then
        assertEquals(memberRepository.findByPhone("010-4172-8563").get().getName(), "한보은");
    }

    @Test
    public void 전화번호_변경() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");
        member.setIdentifier("its' me");

        //when
        Member member_result = memberRepository.save(member);
        member.setPhone("010-1234-5678");
        memberRepository.updatePhone(member.getPhone(), member.getIdentifier());

        //then
        assertEquals(memberRepository.findByPhone(member.getPhone()).get().getIdentifier(), member.getIdentifier());
    }

    @Test
    public void 연령대로_회원_찾기() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");
        member.setAge(2000);

        Member member2 = new Member();
        member2.setName("한보은");
        member2.setPhone("010-1234-5678");
        member2.setAge(1914);

        Member member3 = new Member();
        member3.setName("한보은");
        member3.setPhone("010-9876-5432");
        member3.setAge(2000);

        //when
        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.save(member3);

        //then
        List<Member> list = memberRepository.findByAgeBetween(1994, 2000);
        assertEquals(list.size(), 2);

        List<Member> list2 = memberRepository.findByAgeBetween(1914, 1923);
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
        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.save(member3);

        //then
        List<Member> female = memberRepository.findByGender(Gender.FEMALE);
        assertEquals(female.size(), 2);

        List<Member> male = memberRepository.findByGender(Gender.MALE);
        assertEquals(male.size(), 1);
        assertEquals(male.get(0).getPhone(), "010-1234-5678");
    }

    @Test
    public void 연령대와_성별로_사람_찾기() {
        //given
        Member member = new Member();
        member.setName("박성하");
        member.setPhone("010-4172-8563");
        member.setAge(2000);
        member.setGender(Gender.FEMALE);

        Member member2 = new Member();
        member2.setName("박성하");
        member2.setPhone("010-1234-5678");
        member2.setAge(2000);
        member2.setGender(Gender.MALE);

        Member member3 = new Member();
        member3.setName("한보은");
        member3.setPhone("010-9876-5432");
        member3.setAge(1914);
        member3.setGender(Gender.FEMALE);

        //when
        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.save(member3);

        //then
        List<Member> list = memberRepository.findByAgeBetweenAndGender(1994, 2000, Gender.FEMALE);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getPhone(), "010-4172-8563");

        List<Member> list2 = memberRepository.findByAgeBetweenAndGender(1994, 2000, Gender.MALE);
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
        memberRepository.save(member);
        memberRepository.save(member2);

        //then
        assertEquals(memberRepository.findByPhone("010-4172-8563").get().getAuthority(), Authority.ADMIN);
        assertEquals(memberRepository.findByPhone("010-1234-5678").get().getAuthority(), Authority.USER);
    }
}