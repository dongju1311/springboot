package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.Member;
import com.springboot.shoppy_fullstack_app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
//@CrossOrigin(origins = {"http://localhost:3000"})
public class MemberController {

    //서비스 객체 가져오기
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/idcheck")
    public String idcheck(@RequestBody Member member) {
        boolean result = memberService.idCheck(member.getId());
        String msg = "";
        if(result) msg = "이미 사용중인 아이디 입니다.";
        else msg = "사용이 가능한 아이디 입니다.";

        return msg;
    }

    @PostMapping("/signup")
    public boolean signup(@RequestBody Member member) {
        boolean result = false;
        int rows = memberService.signup(member);
        if(rows==1) result = true;
        return result;
    }


    @PostMapping("/login")
    public String login(@RequestBody Member member) {
        boolean result = memberService.login(member);
        String msg = "";
        if(result) msg = "로그인 성공";
        else msg = "로그인 실패";

        return msg;
    }

}
