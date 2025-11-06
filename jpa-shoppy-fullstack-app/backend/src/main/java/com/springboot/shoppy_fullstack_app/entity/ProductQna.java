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
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 100)
    private String content;
    @Column(length = 1, nullable = false)
    private boolean isComplete;
    @Column(length = 1, nullable = false)
    private boolean isLock;
    @Column(length = 50)
    private String id;
    @Column(nullable = false)
    private int pid;
    @Column(nullable = false)
    private String cdate;
}

