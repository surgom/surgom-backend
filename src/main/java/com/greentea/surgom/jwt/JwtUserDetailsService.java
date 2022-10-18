package com.greentea.surgom.jwt;

import com.greentea.surgom.domain.Member;
import com.greentea.surgom.domain.MemberDto;
import com.greentea.surgom.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByPhone(phone);

        if (member != null) {
            return new MemberDto(member, member.get().getPhone(), member.get().getIdentifier(), false, false, false, false, member.get().getAuthority());
        }
        else
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
    }
}
