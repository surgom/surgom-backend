package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface JWTTokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByPhone(String phone);
    Optional<Token> findByJwtAccess(Object token);
    @Query("update Token t set t.access_token = :access_token where t.refresh_token = :refresh_token")
    @Modifying
    void updateAccess_token(@Param("access_token") String access_token, @Param("refresh_token") String refresh_token);
}
