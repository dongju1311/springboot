package com.springboot.shoppy_fullstack_app.dto;

import com.springboot.shoppy_fullstack_app.entity.ProductQna;
import com.springboot.shoppy_fullstack_app.entity.ProductReturn;
import lombok.Data;

@Data
public class ProductReturnDto {
    private int rid;
    private String title;
    private String description;
    private String list;

    public ProductReturnDto(){}
    public ProductReturnDto(ProductReturn entity){
        this.rid = entity.getRid();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.list = entity.getList();
    }
}
