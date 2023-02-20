package com.greentea.surgom.dto;

import com.greentea.surgom.domain.Authority;
import com.greentea.surgom.domain.Gender;
import com.greentea.surgom.domain.Member;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Builder
@NoArgsConstructor
public class MemberDto {
    @NotNull
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

    public MemberDto(String phone, String nickname, String name, int age, Gender gender, Long point, Authority authority, String identifier) {
        this.phone = phone;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.point = point;
        this.authority = authority;
        this.identifier = identifier;
    }

    public static MemberDto from(Member member) {
        if (member == null) return null;
        
        return MemberDto.builder()
                .phone(member.getPhone())
                .nickname(member.getNickname())
                .name(member.getName())
                .age(member.getAge())
                .gender(member.getGender())
                .point(member.getPoint())
                .authority(member.getAuthority())
                .identifier(member.getIdentifier())
                .build();
    }
}
