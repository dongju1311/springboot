package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.KakaoPayDto;
import com.springboot.shoppy_fullstack_app.entity.Order;
import com.springboot.shoppy_fullstack_app.repository.CartRepository;
import com.springboot.shoppy_fullstack_app.repository.JpaCartRepository;
import com.springboot.shoppy_fullstack_app.repository.JpaOrderRepository;
import com.springboot.shoppy_fullstack_app.repository2222.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final JpaOrderRepository jpaOrderRepository;
    private final CartRepository cartRepository;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, JpaOrderRepository jpaOrderRepository, CartRepository cartRepository){
        this.orderRepository = orderRepository;
        this.jpaOrderRepository = jpaOrderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public int save(KakaoPayDto kakaoPayDto){
        int result = 0;
        //step 1: Orders 테이블 저장
        Order entity = jpaOrderRepository.save(new Order(kakaoPayDto));
        if(entity == null) new Exception("step1 주문 테이블 저장 실패!!");
        //step 2: Order_detail 저장
        int rows = jpaOrderRepository.saveOrderDetail(kakaoPayDto.getOrderId(),
                kakaoPayDto.getPaymentInfo().getDiscountAmount(),
                kakaoPayDto.getCidList());
        if(rows == 0) new Exception("step2 주문 상세 테이블 저장 실패!!");
        //step 3: Cart 테이블 아이템 삭제
//        int cartRows = jpaCartRepository.deleteItemList(kakaoPayDto.getCidList());
//        if(cartRows == 0) new Exception("step3 장바구니 아이템 삭제 실패!!");
//        if(entity != null && rows != 0 && cartRows != 0) result = 1;

        return result;
    }

//    @Override
//    @Transactional
//    public int save(KakaoPayDto kakaoPay){
//        int rows = orderRepository.saveOrders(kakaoPay);
//        if(!(rows == 1)) System.out.println("결제실패");
//
//        int rows_detail = orderRepository.saveOrderDetail(kakaoPay);
//        if(!(rows_detail < 1))System.out.println("결제실패");
//
//        int rows_cart = orderRepository.deleteCartItem(kakaoPay.getCidList());
//        return rows;
//    }

}
