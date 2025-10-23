package com.springboot.shoppy_fullstack_app.dto;

import lombok.Data;

@Data
public class CartListResponse {
    private String id;
    private int pid;
    private String name;
    private String image;
    private int price;
    private int cid;
    private String size;
    private int qty;
    private int totalPrice;
}
