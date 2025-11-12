package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.PageResponseDto;
import com.springboot.shoppy_fullstack_app.dto.SupportDto;

import java.util.List;

public interface SupportService {
    PageResponseDto<SupportDto> findAll(SupportDto support);
    PageResponseDto<SupportDto> findSearchAll(SupportDto support);
}