package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.CartCheckQtyDto;
import com.springboot.shoppy_fullstack_app.dto.CartItemDto;
import com.springboot.shoppy_fullstack_app.dto.CartListResponseDto;
import com.springboot.shoppy_fullstack_app.entity.CartItem;
import com.springboot.shoppy_fullstack_app.repository.JpaCartRepository;
import com.springboot.shoppy_fullstack_app.repository2222.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional //JPA에서 update/delete 작업시 명시적으로 정의 필수!
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final JpaCartRepository jpaCartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, JpaCartRepository jpaCartRepository) {
        this.cartRepository = cartRepository;
        this.jpaCartRepository = jpaCartRepository;
    }

    @Override
    public int deleteItem(CartItemDto cartItem) {
        return jpaCartRepository.deleteItem(cartItem.getCid());
    }

    @Override
    public List<CartListResponseDto> findList(CartItemDto cartItemDto) {
        return jpaCartRepository.findByUsername(cartItemDto.getId());
    }

    @Override
    public CartItemDto getCount(CartItemDto cartItem) {
        int count = jpaCartRepository.countById(cartItem.getId());
        cartItem.setSumQty(count);
        return cartItem;
    }

    @Override
    public int updateQty(CartItemDto cartItemDto) {
        int result = 0;
        if(cartItemDto.getType().equals("+")){
            result = jpaCartRepository.increaseQty(cartItemDto.getCid());
        } else {
            result = jpaCartRepository.decreaseQty(cartItemDto.getCid());
        }
        return result;
    }

    @Override
    public CartItemDto checkQty(CartItemDto cartItemDto) {
        int pid = cartItemDto.getPid();
        String size = cartItemDto.getSize();
        String id = cartItemDto.getId();
        CartCheckQtyDto qtyDto = jpaCartRepository.checkQty(pid,size,id);
        if(qtyDto != null){
        cartItemDto.setCid(qtyDto.getCid());
        cartItemDto.setCheckQty(qtyDto.getCount());
        } else cartItemDto.setCheckQty(0L);
        return cartItemDto;
    }

    @Override
    public int add(CartItemDto cartItemDto) {
        int result = 0;
        CartItem entity = jpaCartRepository.save(new CartItem(cartItemDto));
        if(entity != null) result = 1;
        return result;
    }
}
