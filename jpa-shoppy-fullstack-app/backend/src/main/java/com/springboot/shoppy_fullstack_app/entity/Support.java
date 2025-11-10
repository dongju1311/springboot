package com.springboot.shoppy_fullstack_app.entity;

import com.springboot.shoppy_fullstack_app.dto.SupportDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "support")
@Getter @Setter
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sid;
    private String title;
    private String content;
    @Column(nullable = false)
    private String stype;
    private Integer hits;
    private String rdate;

    //DTO => Entity : DB연동작업
    public Support(){}
    public Support(SupportDto dto){
        this.sid = dto.getSid();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.stype = dto.getStype();
        this.hits = dto.getHits();
        this.rdate = dto.getRdate();
    }
}
