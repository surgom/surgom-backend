package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Gender;
import com.greentea.surgom.domain.Member;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPhone(String phone);
    Optional<Member> findByPhoneAndIdentifier(String phone, String identifier);
    List<Member> findByAgeBetween(int first, int last);
    List<Member> findByGender(Gender gender);
    List<Member> findByAgeBetweenAndGender(int first, int last, Gender gender);
    @Query("update Member m set m.phone = :phone where m.identifier = :identifier")
    @Modifying
    void updatePhone(@Param("phone") String phone, @Param("identifier") String identifier);
}
