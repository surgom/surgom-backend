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
    private String access_token;
    private String refresh_token;
    private String jwt_access_token;
    private String jwt_refresh_token;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_token_phone")
    private Member member;

    @Builder
    public Token(String phone, String access_token, String refresh_token, String jwt_access_token, String jwt_refresh_token) {
        this.phone = phone;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.jwt_access_token = jwt_access_token;
        this.jwt_refresh_token = jwt_refresh_token;
    }

    public Token() {

    }
}
