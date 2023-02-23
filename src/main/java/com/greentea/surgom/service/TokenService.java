package com.greentea.surgom.service;

import com.greentea.surgom.domain.Token;
import com.greentea.surgom.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    public Optional<Token> getToken(String phone) {
        return jwtTokenRepository.findByPhone(phone);
    }
}
