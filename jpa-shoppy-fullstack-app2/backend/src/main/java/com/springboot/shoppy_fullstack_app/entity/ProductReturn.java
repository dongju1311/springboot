package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_return")
@Getter @Setter
public class ProductReturn {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 200)
    private String description;
    @Column(columnDefinition = "JSON")
    private String list;
}
