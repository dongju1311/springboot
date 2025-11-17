package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.ProductDto;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfoDto;
import com.springboot.shoppy_fullstack_app.dto.ProductQnaDto;
import com.springboot.shoppy_fullstack_app.dto.ProductReturnDto;
import com.springboot.shoppy_fullstack_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/return")
    public ProductReturnDto getReturn() {
        return productService.findReturn();
    }

    @PostMapping("/qna")
    public List<ProductQnaDto> qna(@RequestBody ProductDto product) {
        return productService.findQna(product.getPid());
    }

    @PostMapping("/detailinfo")
    public ProductDetailinfoDto detailinfo(@RequestBody ProductDto product) {
        return productService.findDetailinfo(product.getPid());
    }

    @PostMapping("/pid")
    public ProductDto pid(@RequestBody ProductDto product) {
        return productService.findByPid(product.getPid());
    }

    @GetMapping("/all")
    public List<ProductDto> all() {
        return productService.findAll();
    }
}
