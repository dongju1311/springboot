package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Product;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.dto.ProductQna;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Product findByPid(int pid);

    ProductDetailinfo findProductDetailinfo(int pid);

    List<ProductQna> findQna(int pid);
}
