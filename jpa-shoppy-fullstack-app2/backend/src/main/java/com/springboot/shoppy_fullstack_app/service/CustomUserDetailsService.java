package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import com.springboot.shoppy_fullstack_app.entity.Member;
import com.springboot.shoppy_fullstack_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Autowired
    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override //DB 연동
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findById(userId);
        User.UserBuilder b =null;
        if(!member.isEmpty()){
            //member가 null이 아니면 USER 객체 즉 회원으로 인증되어 SecurityContext에 저장됨
            b = User.withUsername(member.get().getId())
                    .password(member.get().getPwd())    // BCrypt로 저장되어 있어야 함
                    .roles(member.get().getRole());    // 필요 시 DB에서 권한 매핑 : 'ROLE_' 자동매핑
        }
        return b.build();
    }
}
