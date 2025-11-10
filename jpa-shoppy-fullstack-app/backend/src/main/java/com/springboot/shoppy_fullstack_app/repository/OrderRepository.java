package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.KakaoPayDto;

import java.util.List;

public interface OrderRepository {
    int saveOrders(KakaoPayDto kakaoPay);
    int saveOrderDetail(KakaoPayDto kakaoPay);
    int deleteCartItem(List<Integer> cidList);
}
