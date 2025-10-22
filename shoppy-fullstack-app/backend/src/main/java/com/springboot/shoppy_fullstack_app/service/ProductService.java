package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.Product;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.dto.ProductQna;
import com.springboot.shoppy_fullstack_app.dto.ProductReturn;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findByPid(int pid);

    ProductDetailinfo findDetailinfo(int pid);

    List<ProductQna> findQna(int pid);

    ProductReturn findReturn();
}
