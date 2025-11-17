package com.springboot.shoppy_fullstack_app.entity;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Setter
@Getter
public class Member {
    @Id
    @Column(name = "id", length = 50)
    private String id;
    private String pwd;
    private String name;
    private String phone;
    private String email;
    private LocalDate mdate;
    private String role;
    //Entity는 생성자를 반드시 정의해야함!!!
    public Member(){}
    public Member(MemberDto memberDto){
        this.id = memberDto.getId();
        this.pwd = memberDto.getPwd();
        this.name = memberDto.getName();
        this.phone = memberDto.getPhone();
        this.email = memberDto.getEmail();
        this.mdate = LocalDate.now();
        this.role = memberDto.getRole();
    }
}
