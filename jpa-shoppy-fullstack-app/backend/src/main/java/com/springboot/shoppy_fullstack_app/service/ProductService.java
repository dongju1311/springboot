package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.ProductDto;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfoDto;
import com.springboot.shoppy_fullstack_app.dto.ProductQnaDto;
import com.springboot.shoppy_fullstack_app.dto.ProductReturnDto;

import java.util.List;

public interface ProductService {
    ProductReturnDto findReturn();
    List<ProductQnaDto>  findQna(int pid);
    ProductDetailinfoDto findDetailinfo(int pid);
    ProductDto findByPid(int pid);
    List<ProductDto> findAll();
}
