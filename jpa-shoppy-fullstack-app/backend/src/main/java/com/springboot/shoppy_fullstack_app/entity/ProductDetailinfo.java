package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_detailinfo")
@Getter @Setter
public class ProductDetailinfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int did;
    @Column(nullable = false)
    private int pid;
    @Column(columnDefinition = "JSON")
    private String list;
    @Column(length = 100, nullable = false)
    private String titleEn;
    @Column(length = 100, nullable = false)
    private String titleKo;


}
