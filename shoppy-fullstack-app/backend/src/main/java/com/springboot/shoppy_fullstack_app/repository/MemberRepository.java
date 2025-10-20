package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Member;

public interface MemberRepository {
    int save(Member member);

    Long findById(String id);

    String login(String id);
}
