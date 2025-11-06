package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.ProductDto;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfoDto;
import com.springboot.shoppy_fullstack_app.dto.ProductQnaDto;
import com.springboot.shoppy_fullstack_app.dto.ProductReturnDto;

import java.util.List;

public interface ProductRepository {
    ProductReturnDto findReturn();
    List<ProductQnaDto> findQna(int pid);
    ProductDetailinfoDto findProductDetailinfo(int pid);
    ProductDto findByPid(int pid);
    List<ProductDto> findAll();
}
