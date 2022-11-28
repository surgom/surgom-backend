package com.greentea.surgom.jwt;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JWTUserDetailsService implements UserDetailsService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByPhone(phone);

        if (member != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(member.get().getName())
                    .password(phone)
                    .authorities(member.get().getAuthority())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
        else
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
    }
}
