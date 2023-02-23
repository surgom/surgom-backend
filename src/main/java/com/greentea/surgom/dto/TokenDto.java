package com.greentea.surgom.dto;

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
}
