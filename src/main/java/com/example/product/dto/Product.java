package com.example.product.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "pattachdata")
public class Product {
    //클라이언트 <-- 서버
    private int pno;

    // 클라이언트 <--> 서버
    private String pname;
    private String pcontent;
    private String pwriter;
    private Integer pprice;
    private Date pdate;
    private int phitcount;

    // 클라이언트 --> 서버
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile pattach;

    // 서버 --> DB
    private String pattachoname;
    private String pattachtype;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] pattachdata;
}
