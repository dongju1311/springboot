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
    @Column(name = "pwd", length = 100, nullable = false)
    private String pwd;
    @Column(name = "name", length = 20, nullable = false)
    private String name;
    @Column(name = "phone", length = 13)
    private String phone;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "mdate")
    private LocalDate mdate;
    //Entity는 생성자를 반드시 정의해야함!!!
    public Member(){}
    public Member(MemberDto memberDto){
        this.id = memberDto.getId();
        this.pwd = memberDto.getPwd();
        this.name = memberDto.getName();
        this.phone = memberDto.getPhone();
        this.email = memberDto.getEmail();
        this.mdate = LocalDate.now();
    }
}
