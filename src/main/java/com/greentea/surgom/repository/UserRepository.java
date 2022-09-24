package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Member;

import java.util.List;

public interface UserRepository {
    Long save(Member member);
    Member findById(Long id);
    void delete(Long id);
    void deleteAll();
    List<Member> findAllWithAge_range(String range);
    List<Member> findAllWithGender(char gender);
    void update(Member member);
}
