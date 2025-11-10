package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.SupportDto;

import java.util.List;

public interface SupportService {
    List<SupportDto> findAll(SupportDto support);
}
