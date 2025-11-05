package com.springboot.shoppy_fullstack_app.dto;

import lombok.Data;

@Data
public class KakaoApproveResponse {
    private String aid;
    private String tid;
    private String cid;
    private String partnerOrderId;
    private String partnerUserId;
    private String paymentMethodType;
    private String itemName;
    private int quantity;
    private int amountTotal;
}

