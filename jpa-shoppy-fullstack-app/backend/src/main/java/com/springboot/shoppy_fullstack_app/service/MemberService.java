package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;

public interface MemberService {
    boolean login(MemberDto memberDto);
    int signup(MemberDto memberDto);
    boolean idCheck(String id);
}
