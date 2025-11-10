package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.SupportDto;
import com.springboot.shoppy_fullstack_app.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/support")
public class SupportController {

    private SupportService supportService;

    @Autowired
    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }

    @PostMapping("/list")
    public List<SupportDto> list(@RequestBody SupportDto support) {
        return supportService.findAll(support);
    }
}
