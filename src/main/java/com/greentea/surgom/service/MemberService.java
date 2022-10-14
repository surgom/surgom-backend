package com.greentea.surgom.service;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByPhone(phone);

        if (member == null)
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");

        return org.springframework.security.core.userdetails.User
                .withUsername(phone)
                .
    }
}
