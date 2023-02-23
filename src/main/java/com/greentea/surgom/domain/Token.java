package com.greentea.surgom.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Token {
    @Id
    private String phone;
    private String jwtAccessToken;
    private String jwtRefreshToken;

    public Token(String phone, String jwtAccessToken, String jwtRefreshToken) {
        this.phone = phone;
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }
}
