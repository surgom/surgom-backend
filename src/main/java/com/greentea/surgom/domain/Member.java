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
public class Member {
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

    public Member(String phone, String nickname, String name, int age, Gender gender, Long point, Authority authority, String identifier) {
        this.phone = phone;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.point = point;
        this.authority = authority;
        this.identifier = identifier;
    }
}

