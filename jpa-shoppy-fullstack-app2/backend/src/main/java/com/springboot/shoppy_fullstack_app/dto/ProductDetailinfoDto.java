package com.springboot.shoppy_fullstack_app.dto;

import com.springboot.shoppy_fullstack_app.entity.ProductDetailinfo;
import lombok.Data;

@Data
public class ProductDetailinfoDto {
    private int did;
    private int pid;
    private String list;
    private String titleEn;
    private String titleKo;

    public ProductDetailinfoDto(){}
    public ProductDetailinfoDto(ProductDetailinfo entity){
        this.did = entity.getDid();
        this.pid = entity.getProduct().getPid();
        this.list = entity.getList();
        this.titleEn = entity.getTitleEn();
        this.titleKo = entity.getTitleKo();
    }
}
