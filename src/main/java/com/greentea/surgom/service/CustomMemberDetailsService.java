package com.greentea.surgom.service;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component("userDetailsService")
public class CustomMemberDetailsService implements UserDetailsService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String phone) {
        return memberRepository.findByPhone(phone)
                .map(member -> createUser(phone, member))
                .orElse(null);
    }

    private org.springframework.security.core.userdetails.User createUser(String phone, Member member) {

        return new org.springframework.security.core.userdetails.User(member.getPhone(),
                member.getName(),
                Collections.singleton(member.getAuthority()));
    }
}
