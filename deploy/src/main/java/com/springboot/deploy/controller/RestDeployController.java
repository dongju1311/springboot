package com.springboot.deploy.controller;

import com.springboot.deploy.dto.Member;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestDeployController {
    @PostMapping("/restLogin")
    public Map<String, Object> restLogin(@RequestBody Member member){
        boolean result = false;
        if(member.getId().equals("test") && member.getPass().equals("1234")) result = true;
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", result);
        response.put("member", member);
        return response;
    }
    @PostMapping("/restSignup")
    public Member restSignup(@RequestBody Member member){
        return member;
    }

}
