package com.springboot.study.controller;

import com.springboot.study.dto.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @GetMapping("/login")
    public String login(){
        return "login"; //로그인 화면, view name --> templates
    }
    /** Spring Legacy 버전 --> ModelAndView 객체를 활용하여 데이터 및 view 전송
    @PostMapping("/login")
    public ModelAndView login(@RequestParam String id, @RequestParam String pass) {
        ModelAndView model = new ModelAndView();
        String result = "";
        if(id.equals("test") && pass.equals("1234")) result="[ModelAndView]로그인성공";
        else result="[ModelAndView]로그인실패";
        model.addObject("result", result);
        model.setViewName("loginResult");
        return model;
    }
     */
    @PostMapping("/login")
    public String login(Model model, Member member){
        String result = "";
        if(member.getId().equals("test") && member.getPass().equals("1234")) result="로그인성공";
        else result="로그인실패";
        model.addAttribute("result", result);
        return "loginResult"; //view name : templates --> loginResult.html
    }
    @GetMapping("/signup")
    public String signup(){
        return "signup"; //회원가입 화면, view name --> templates
    }
    @PostMapping("/signup")
    public String signup(Member member, Model model){
        model.addAttribute("member", member);
        return "signupResult";
    }
}
