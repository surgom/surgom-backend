package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Member;

import java.util.List;

public interface MemberRepository {
    String save(Member member);
    Member findByPhone(String phone);
    void delete(String phone);
    void deleteAll();
    List<Member> findAllWithAge_range(String range);
    List<Member> findAllWithGender(char gender);
    void update(Member member);
}
