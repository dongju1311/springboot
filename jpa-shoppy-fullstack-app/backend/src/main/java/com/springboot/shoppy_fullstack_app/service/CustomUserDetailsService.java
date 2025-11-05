package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import com.springboot.shoppy_fullstack_app.jpa_repository.JpaMemberRepository;
import com.springboot.shoppy_fullstack_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final JpaMemberRepository jpaMemberRepository;

    @Autowired
    public CustomUserDetailsService(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    @Override //DB 연동
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        MemberDto member = jpaMemberRepository.findByMember(userId)
                .orElseThrow(() -> new UsernameNotFoundException("not found: " + userId));
        //member가 null이 아니면 USER 객체 즉 회원으로 인증되어 SecurityContext에 저장됨
        User.UserBuilder b = User.withUsername(member.getId())
                .password(member.getPwd())    // BCrypt로 저장되어 있어야 함
                .roles("USER");                   // 필요 시 DB에서 권한 매핑
        return b.build();
    }
}
