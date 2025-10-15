package com.springboot.study.controller;

import com.springboot.study.dto.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController //내부에 @ResponseBody가 포함, Map 객체 생성 없이 JSON 객체 바로 전송
public class MemberRestController {

    @PostMapping("/restLogin")
    public Map<String, Object> restLogin(@RequestBody Member member) {
        boolean result = false;
        if(member.getId().equals("test") && member.getPass().equals("1234")) result = true;

        //Map 객체(key, value)를 생성하여 전송  --> 자동으로  JSON 객체로 변환
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", result);
        response.put("member", member);

        return response; //호출한 페이지로 문자열 혹은 JSON 객체로 전송 : { "result": true }
    }
    @PostMapping("/restSignup")
    public Member restSignup(@RequestBody Member member) {
//        Map<String, Object> response = new HashMap<String, Object>();
//        response.put("member", member);
        return member;
    }
}
