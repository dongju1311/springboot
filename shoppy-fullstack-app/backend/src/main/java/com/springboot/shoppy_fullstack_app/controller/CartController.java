package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.CartItem;
import com.springboot.shoppy_fullstack_app.dto.CartListResponse;
import com.springboot.shoppy_fullstack_app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }
    @PostMapping("/add")
    public int add(@RequestBody CartItem cartItem){
        return cartService.add(cartItem);
    }
    @PostMapping("/checkQty")
    public CartItem checkQty(@RequestBody CartItem cartItem){
        return cartService.checkQty(cartItem);
    }
    @PostMapping("/updateQty")
    public int updateQty(@RequestBody CartItem cartItem){
        return cartService.updateQty(cartItem);
    }
    @PostMapping("/count")
    public CartItem count(@RequestBody CartItem cartItem){
        return cartService.getCount(cartItem);
    }
    @PostMapping("/list")
    public List<CartListResponse> findList(@RequestBody CartItem cartItem) {
        return cartService.findList(cartItem);}
    @PostMapping("/deleteItem")
    public int deleteItem(@RequestBody CartItem cartItem) {return cartService.deleteItem(cartItem);}
}
