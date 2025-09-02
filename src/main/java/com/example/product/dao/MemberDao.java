package com.example.product.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.product.dto.Member;

// create table member(
//     mid     varchar(20)     primary key,
//     mname   varchar(20)     not null,
//     mpassword varchar(200) not null,
//     mrole    varchar(20) not null
// );

@Mapper
public interface MemberDao {
    public int insert(Member member);
    public Member selectByMid(String mid);
    public int update(Member member);
    public int delete(String mid);
}
