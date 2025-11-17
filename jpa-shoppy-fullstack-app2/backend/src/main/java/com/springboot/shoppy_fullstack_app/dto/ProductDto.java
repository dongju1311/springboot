package com.springboot.shoppy_fullstack_app.dto;

import com.springboot.shoppy_fullstack_app.entity.Product;
import lombok.Data;

@Data
public class ProductDto {
    private int pid;
    private String name;
    private long price;
    private String info;
    private double rate;
    private String image;
    private String imgList;

    //Entity <=> Dto 변환
    public ProductDto(){}
    public ProductDto(Product entity){
        this.pid = entity.getPid();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.info = entity.getInfo();
        this.rate = entity.getRate();
        this.image = entity.getImage();
        this.imgList = entity.getImgList();
    }
}

