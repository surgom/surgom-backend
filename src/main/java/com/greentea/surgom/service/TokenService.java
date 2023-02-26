package com.greentea.surgom.service;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.Token;
import com.greentea.surgom.dto.MemberDto;
import com.greentea.surgom.dto.TokenDto;
import com.greentea.surgom.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public Optional<Token> getToken(String phone) {
        return tokenRepository.findByPhone(phone);
    }

    @Transactional
    public TokenDto signUp(TokenDto tokenDto) {

        Token token = Token.builder()
                .phone(tokenDto.getPhone())
                .jwtAccessToken(tokenDto.getJwtAccessToken())
                .jwtRefreshToken(tokenDto.getJwtRefreshToken())
                .build();

        return TokenDto.from(tokenRepository.save(token));
    }
}
