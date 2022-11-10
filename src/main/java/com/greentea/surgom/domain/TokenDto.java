package com.greentea.surgom.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Setter
public class TokenDto {
    @Id
    private String phone;
    private String nickname;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Long point;
    @Enumerated(EnumType.STRING)
    private Authority authority;
    private String identifier;
    private String jwt_access_token;
    private String jwt_refresh_token;
}
