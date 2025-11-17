package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product")
@Getter @Setter
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    private String name;
    @Column(name = "price", columnDefinition = "MEDIUMTEXT")
    private long price;
    private String info;
    private double rate;
    private String image;
    @Column(columnDefinition = "JSON")
    private String imgList;
    //Product(1) : (1..n) ProductDetailinfo 엔티티 정의 - 필드 값
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ProductDetailinfo detailinfo;
    //Product(1) : (1..n) ProductQna 엔티티 정의
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductQna> qna;
}
