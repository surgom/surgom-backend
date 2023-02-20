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
public interface JwtTokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByPhone(String phone);
    Optional<Token> findByJwtAccessToken(Object token);
    @Query("update Token t set t.accessToken = :access_token where t.refreshToken = :refresh_token")
    @Modifying
    void updateAccess_token(@Param("access_token") String access_token, @Param("refresh_token") String refresh_token);
}
