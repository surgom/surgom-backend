package com.greentea.surgom.domain;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_token_phone")
    private Member member;
}
