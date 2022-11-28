package com.greentea.surgom.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Token {
    @Id
    private String phone;
    private String accessToken;
    private String refreshToken;
    private String jwtAccessToken;
    private String jwtRefreshToken;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_token_phone")
    private Member member;

    @Builder
    public Token(String phone, String accessToken, String refreshToken, String jwtAccessToken, String jwtRefreshToken) {
        this.phone = phone;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }

    @Builder
    public Token() {

    }
}
