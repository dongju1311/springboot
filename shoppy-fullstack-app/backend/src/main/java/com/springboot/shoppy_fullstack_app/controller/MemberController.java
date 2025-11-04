package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.Member;
import com.springboot.shoppy_fullstack_app.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member")
//@CrossOrigin(origins = {"http://localhost:3000"})
public class MemberController {
    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final HttpSessionSecurityContextRepository contextRepository;

    @Autowired
    public MemberController(MemberService memberService,
                            AuthenticationManager authenticationManager,
                            HttpSessionSecurityContextRepository contextRepository) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
        this.contextRepository = contextRepository;
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
    public ResponseEntity<?> login(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        try {
            //1. 인증요청
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken.unauthenticated(member.getId(), member.getPwd());
            //2. 인증처리
            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);
            System.out.println("인증 성공: " + authenticationResponse.getPrincipal());

            //3. 컨텍스트에 보관
            var context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authenticationResponse);
            SecurityContextHolder.setContext(context);

            // SecurityContext 세션에 "명시 저장" (requireExplicitSave(true)일 때 필수)
            contextRepository.saveContext(context, request, response);

            //4. 로그인 성공 시 CSRF 토큰을 재발행을 위해 브라우저 토큰 null 처리
            var xsrf = new Cookie("XSRF-TOKEN", null);
            xsrf.setPath("/");               // ← 기존과 동일
            xsrf.setMaxAge(0);               // ← 즉시 만료
            xsrf.setHttpOnly(false);          // 개발 중에도 HttpOnly 유지 권장
            // cookie.setSecure(true);         // HTTPS에서만. 로컬 http면 주석
            // cookie.setDomain("localhost");  // 기존 쿠키가 domain=localhost였다면 지정
            response.addCookie(xsrf);

            return ResponseEntity.ok(Map.of("login", true,
                    "userId", member.getId()));

        } catch(Exception e) {
            //로그인 실패
            return ResponseEntity.ok(Map.of("login", false));
        }
//        ResponseEntity<?> response = null;
//        boolean result = memberService.login(member);
//        if(result) {
//        //세션 생성 - true 또는 빈 값은 생성 파라미터
//        //기존 세션 가져오기 - false
//        HttpSession session = request.getSession();
//        session.setAttribute("sid","test");
//        response = ResponseEntity.ok(Map.of("login",true));
//        } else {
//        response = ResponseEntity.ok(Map.of("login",false));
//        }
//        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
        // 1. 세션이 없으면 생성하지 않고 null 반환 (로그아웃 시 표준 방식)
        HttpSession session = request.getSession(false);

        // 2. 세션이 존재하면 무효화
        if(session != null) {
            session.invalidate(); // 서버 세션 무효화 (JSESSIONID 삭제 명령 포함)
        }

        // 3. JSESSIONID 만료 쿠키 전송 (Path/Domain 꼭 기존과 동일)
        var cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");               // ← 기존과 동일
        cookie.setMaxAge(0);               // ← 즉시 만료
        cookie.setHttpOnly(true);          // 개발 중에도 HttpOnly 유지 권장
        // cookie.setSecure(true);         // HTTPS에서만. 로컬 http면 주석
        // cookie.setDomain("localhost");  // 기존 쿠키가 domain=localhost였다면 지정
        response.addCookie(cookie);
        
        // 4. CSRF 토큰을 재발행하여 출력
        var xsrf = new Cookie("XSRF-TOKEN", null);
        xsrf.setPath("/");               // ← 기존과 동일
        xsrf.setMaxAge(0);               // ← 즉시 만료
        xsrf.setHttpOnly(false);          // 개발 중에도 HttpOnly 유지 권장
        // cookie.setSecure(true);         // HTTPS에서만. 로컬 http면 주석
        // cookie.setDomain("localhost");  // 기존 쿠키가 domain=localhost였다면 지정
        response.addCookie(xsrf);

         return ResponseEntity.ok(Map.of("logout", true));
    }
}
