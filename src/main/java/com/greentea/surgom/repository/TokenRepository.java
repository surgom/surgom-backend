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
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByPhone(String phone);
    Optional<Token> findByJwtAccessToken(String jwtAccessToken);
    @Query("update Token t set t.jwtAccessToken = :jwtAccessToken where t.jwtRefreshToken = :jwtRefreshToken")
    @Modifying
    void updateAccess_token(@Param("jwtAccessToken") String jwtAccessToken, @Param("jwtRefreshToken") String jwtRefreshToken);
}
