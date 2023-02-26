package com.greentea.surgom.dto;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class TokenDto {
    private String phone;
    private String jwtAccessToken;
    private String jwtRefreshToken;

    public TokenDto(String phone, String jwtAccessToken, String jwtRefreshToken) {
        this.phone = phone;
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }

    public static TokenDto from(Token token) {
        if (token == null) return null;

        return TokenDto.builder()
                .phone(token.getPhone())
                .jwtAccessToken(token.getJwtAccessToken())
                .jwtRefreshToken(token.getJwtRefreshToken())
                .build();
    }
}
