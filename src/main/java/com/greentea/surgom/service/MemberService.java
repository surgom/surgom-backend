package com.greentea.surgom.service;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.repository.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemoryMemberRepository memoryMemberRepository;

    @Transactional
    public String join(Member member) {
        validateDuplicateMember(member);
        memoryMemberRepository.save(member);
        return member.getPhone();
    }

    private void validateDuplicateMember(Member member) {
        Member byPhone = memoryMemberRepository.findByPhone(member.getPhone());
        if (byPhone != null)
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public Member findOne(String memberPhone) {
        return memoryMemberRepository.findByPhone(memberPhone);
    }

    public List<Member> findAll(String range) {
        return memoryMemberRepository.findAllWithAge_range(range);
    }

    public List<Member> findALl(char gender) {
        return memoryMemberRepository.findAllWithGender(gender);
    }

    public List<Member> findAll() {return memoryMemberRepository.findAll();}

    public void withdraw(Member member) {
        memoryMemberRepository.delete(member.getPhone());
    }

    public void withdrawAll() {
        memoryMemberRepository.deleteAll();
    }

    public void update(Member member) {
        memoryMemberRepository.update(member);
    }
}
