package com.springboot.shoppy_fullstack_app.dto;

import lombok.Data;

@Data
public class KakaoPay {
    private String orderId;
    private String userId;
    private String itemName;
    private String qty;
    private String totalAmount;
}
