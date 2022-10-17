package com.greentea.surgom.security;

import com.greentea.surgom.domain.Authority;
import com.greentea.surgom.domain.Gender;
import com.greentea.surgom.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
public class SessionMember implements Serializable {
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

    public SessionMember() {
    }

    public SessionMember(Member member) {
        this.phone = member.getPhone();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.age = member.getAge();
        this.gender = member.getGender();
        this.point = member.getPoint();
        this.authority = member.getAuthority();
        this.identifier = member.getIdentifier();
    }
}
