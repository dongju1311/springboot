package com.springboot.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    /**
     * http로 호출하는 서비스 context path 주소를 매핑하는 작업
     * 예) /hello
     */
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("pathName","hello");
        model.addAttribute("data","홍길동");
        return "hello"; //view name
    }
    @GetMapping("/hello2")
    public String hello2(Model model) {
        model.addAttribute("pathName","hello2");
        model.addAttribute("data","홍길동 친구");
        return "hello"; //view name
    }
}
