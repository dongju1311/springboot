package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "product_qna")
@Getter @Setter
public class ProductQna {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qid;
    private String title;
    private String content;
    private boolean isComplete;
    private boolean isLock;
    private String id;
    private String cdate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid",nullable = false)
    private Product product;
}

