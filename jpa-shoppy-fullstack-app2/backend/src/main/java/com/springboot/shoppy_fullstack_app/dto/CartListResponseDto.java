package com.springboot.shoppy_fullstack_app.dto;

import com.springboot.shoppy_fullstack_app.entity.CartListView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
//@AllArgsConstructor
public class CartListResponseDto {
    private int cid;
    private String id;
    private String mname;
    private String phone;
    private String email;
    private int pid;
    private String name;
    private String info;
    private String image;
    private int price;
    private String size;
    private Long qty;
    private int totalPrice;

    public CartListResponseDto(){}
    public CartListResponseDto(CartListView v){
        this.cid = v.getCid();
        this.id = v.getId();
        this.mname = v.getMname();
        this.phone = v.getPhone();
        this.email = v.getEmail();
        this.pid = v.getPid();
        this.name = v.getName();
        this.info = v.getInfo();
        this.image = v.getImage();
        this.price = v.getPrice();
        this.size = v.getSize();
        this.qty = v.getQty();
        this.totalPrice = v.getTotalPrice();
    }
}
