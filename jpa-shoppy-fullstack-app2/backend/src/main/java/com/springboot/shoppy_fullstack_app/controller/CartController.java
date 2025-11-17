package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.CartItemDto;
import com.springboot.shoppy_fullstack_app.dto.CartListResponseDto;
import com.springboot.shoppy_fullstack_app.service.CartService;
import com.springboot.shoppy_fullstack_app.service.KakaoPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    private KakaoPayService kakaoPayService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
        this.kakaoPayService = kakaoPayService;
    }

    @PostMapping("/deleteItem")
    public int deleteItem(@RequestBody CartItemDto cartItem) {
        return cartService.deleteItem(cartItem);
    }

    @PostMapping("/list")
    public List<CartListResponseDto> findList(@RequestBody CartItemDto cartItem, HttpServletRequest request) {
//        HttpSession session = request.getSession(false); //기존 생성 세션 가져오기
//        String sid = (String) session.getAttribute("sid");
//        String ssid = session.getId();
//        ResponseEntity<?> response = null;
//        if(ssid != null && sid != null){
//            List<CartListResponse> list =  cartService.findList(cartItem);
//            response = ResponseEntity.ok(list);
//        } else {
//            response = ResponseEntity.ok(Map.of("message", false));
//        }
//        return response;
        return cartService.findList(cartItem);
    }

    @PostMapping("/count")
    public CartItemDto count(@RequestBody CartItemDto cartItem) {
        return cartService.getCount(cartItem);
    }

    @PostMapping("/updateQty")
    public int  updateQty(@RequestBody CartItemDto cartItem) {
        System.out.println("updateQty :: " + cartItem);
        return cartService.updateQty(cartItem);
    }

    @PostMapping("/checkQty")
    public CartItemDto checkQty(@RequestBody CartItemDto cartItem) {
        System.out.println("checkQty" + cartItem.getPid() + cartItem.getSize() + cartItem.getId());
        return cartService.checkQty(cartItem);
    }

    @PostMapping("/add")
    public int add(@RequestBody CartItemDto cartItem) {
        return cartService.add(cartItem);
    }
}
