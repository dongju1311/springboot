package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.CartItemDto;
import com.springboot.shoppy_fullstack_app.dto.CartListResponseDto;

import java.util.List;

public interface CartRepository {
    int deleteItem(CartItemDto cartItem);
    List<CartListResponseDto> findList(CartItemDto cartItem);
    CartItemDto getCount(CartItemDto cartItem);
    int updateQty(CartItemDto cartItem);
    CartItemDto checkQty(CartItemDto cartItem);
    int add(CartItemDto cartItem);
}
