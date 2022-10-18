package com.greentea.surgom.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Collection;

public class MemberDto extends User {
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

    public MemberDto(Member member, String phone, String identifier, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(phone, identifier, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.phone = member.getPhone();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.age = member.getAge();
        this.gender = member.getGender();
        this.point = member.getPoint();
        this.authority = member.getAuthority();
        this.identifier = member.getIdentifier();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }
}
