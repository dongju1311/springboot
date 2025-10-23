package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.CartItem;
import com.springboot.shoppy_fullstack_app.dto.CartListResponse;
import com.springboot.shoppy_fullstack_app.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    private CartRepository cartRepository;

    @Autowired
    public CartServiceImpl (CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public int add(CartItem cartItem){
        return cartRepository.add(cartItem);
    }

    @Override
    public CartItem checkQty(CartItem cartItem){
        return cartRepository.checkQty(cartItem);
    }

    @Override
    public int updateQty(CartItem cartItem){
        return cartRepository.updateQty(cartItem);
    }

    @Override
    public CartItem getCount(CartItem cartItem) {return cartRepository.getCount(cartItem);}

    @Override
    public List<CartListResponse> findList(CartItem cartItem) {return cartRepository.findList(cartItem);}

    @Override
    public int deleteItem(CartItem cartItem) {return cartRepository.deleteItem(cartItem);}
}
