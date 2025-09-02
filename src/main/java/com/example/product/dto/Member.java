package com.example.product.dto;

import lombok.Data;

@Data
public class Member {
    private String mid;
    private String mname;
    private String mpassword;
    private String mrole; //상품등록용 관리자 롤, 상품구매용 유저롤 구분
}