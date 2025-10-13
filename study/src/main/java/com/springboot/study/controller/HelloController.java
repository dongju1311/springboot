package com.springboot.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    /**
     * http로 호출하는 서비스 context path 주소를 매핑하는 작업
     * 예) /hello
     */
    @GetMapping("/hello")
    public String hello() {
        System.out.println("-----> HelloController!!");
        return "hello"; //view name
    }
}
