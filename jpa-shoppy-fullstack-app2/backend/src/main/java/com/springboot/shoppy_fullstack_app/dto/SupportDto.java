package com.springboot.shoppy_fullstack_app.dto;

import com.springboot.shoppy_fullstack_app.entity.Support;
import lombok.Data;

@Data
public class SupportDto {
    private int sid;
    private String title;
    private String content;
    private String stype;
    private int hits;
    private String rdate;
    private int rowNumber; // 페이징 처리 후 출력 되는 행번호
    private int currentPage;
    private int pageSize;
    private String type;
    private String keyword;

    //Entity => DTO : Front 결과 출력
    public SupportDto(){}
    public SupportDto(Support entity){
        this.sid = entity.getSid();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.stype = entity.getStype();
        this.hits = entity.getHits();
        this.rdate = entity.getRdate();
    }
}

