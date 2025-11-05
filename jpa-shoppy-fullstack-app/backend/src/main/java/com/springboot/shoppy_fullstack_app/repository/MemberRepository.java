package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;

import java.util.Optional;

public interface MemberRepository {
    String login(String id);
    int save(MemberDto memberDto);
    Long findById(String id);
    Optional<MemberDto> findByMember(String id);
}
