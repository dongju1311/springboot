package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.ProductDto;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfoDto;
import com.springboot.shoppy_fullstack_app.dto.ProductQnaDto;
import com.springboot.shoppy_fullstack_app.dto.ProductReturnDto;
import com.springboot.shoppy_fullstack_app.entity.Product;
import com.springboot.shoppy_fullstack_app.entity.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.entity.ProductQna;
import com.springboot.shoppy_fullstack_app.entity.ProductReturn;
import com.springboot.shoppy_fullstack_app.jpa_repository.JpaProductRepository;
import com.springboot.shoppy_fullstack_app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//@Transactional
public class ProductServiceImpl implements ProductService {
    private final JpaProductRepository jpaProductRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public ProductReturnDto findReturn() {
        ProductReturn entity = jpaProductRepository.findReturn();
        return new ProductReturnDto(entity);
    }

    @Override
    public List<ProductQnaDto> findQna(int pid) {
        List<ProductQnaDto> list = new ArrayList<>();
        List<ProductQna> entityList = jpaProductRepository.findQna(pid);
        entityList.forEach(entity -> list.add(new ProductQnaDto(entity)));
        return list;
    }

    @Override
    public ProductDetailinfoDto findDetailinfo(int pid) {
        ProductDetailinfo entity = jpaProductRepository.findProductDetailinfo(pid);
        return new ProductDetailinfoDto(entity);
    }

    @Override
    public ProductDto findByPid(int pid) {
        Product entity = jpaProductRepository.findByPid(pid);
        return new ProductDto(entity);
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> dlist = new ArrayList<>();
        List<Product> list = jpaProductRepository.findAll();
        list.forEach((product -> dlist.add(new ProductDto(product))));
        return dlist;
    }
}
