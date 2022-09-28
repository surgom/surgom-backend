package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Member;

import java.util.List;

public interface MemberRepository {
    String save(Member member);
    Member findByPhone(String phone);
    void delete(String phone);
    void deleteAll();
    List<Member> findAll();
    List<Member> findAllWithAge_range(int first, int last);
    List<Member> findAllWithGender(String gender);
    List<Member> findAllWithAgeAndGender(int first, int last, String gender);
    void update(Member member);
}
