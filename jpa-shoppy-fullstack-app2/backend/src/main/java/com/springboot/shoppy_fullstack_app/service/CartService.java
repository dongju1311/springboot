package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.CartItemDto;
import com.springboot.shoppy_fullstack_app.dto.CartItemRequestDto;
import com.springboot.shoppy_fullstack_app.dto.CartItemResponseDto;
import com.springboot.shoppy_fullstack_app.dto.CartListResponseDto;

import java.util.List;

public interface CartService {
    int deleteItem(CartItemRequestDto requestDto);
    List<CartListResponseDto> findList(CartItemRequestDto requestDto);
    CartItemResponseDto getCount(CartItemRequestDto requestDto);
    int updateQty(CartItemRequestDto requestDto);
    CartItemResponseDto checkQty(CartItemRequestDto requestDto);
    int add(CartItemRequestDto requestDto);
}
