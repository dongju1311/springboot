package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.Member;
import com.springboot.shoppy_fullstack_app.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member")
//@CrossOrigin(origins = {"http://localhost:3000"})
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/idcheck")
    public String idcheck(@RequestBody Member member) {
        boolean result = memberService.idCheck(member.getId());  //아이디 O: 1, X: 0
        String msg = "";
        if(result) msg = "이미 사용중인 아이디 입니다.";
        else msg = "사용이 가능한 아이디 입니다.";
        return msg;
    }

    @PostMapping("/signup")
    public boolean signup(@RequestBody Member member) {
        boolean result = false;
        int rows = memberService.signup(member);
        if(rows == 1) result = true;
        return result;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member, HttpServletRequest request) {
        ResponseEntity<?> response = null;
        boolean result = memberService.login(member);
        if(result) {
        //세션 생성 - true 또는 빈 값은 생성 파라미터
        //기존 세션 가져오기 - false
        HttpSession session = request.getSession();
        session.setAttribute("sid","test");
        response = ResponseEntity.ok(Map.of("login",true));
        } else {
        response = ResponseEntity.ok(Map.of("login",false));
        }
        return response;
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String ssid = session.getId();
        String sid = (String)session.getAttribute("sid");
//        ResponseEntity<?> response = null;
        if(ssid != null && sid != null){
            session.invalidate();//세션을 삭제 - 스프링의 세션 테이블에서 삭제됨
            var cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/");        //기존과 동일
            cookie.setMaxAge(0);        //즉시 만료
            cookie.setHttpOnly(true);   //개발 중에도 HttpOnly 유지 권장
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(true);
    }
}
