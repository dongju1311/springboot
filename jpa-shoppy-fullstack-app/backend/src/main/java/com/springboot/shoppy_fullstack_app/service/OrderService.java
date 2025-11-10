package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.KakaoPayDto;

public interface OrderService {
    int save(KakaoPayDto kakaoPay);
}
