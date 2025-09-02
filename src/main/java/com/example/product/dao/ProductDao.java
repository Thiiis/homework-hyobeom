package com.example.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.product.dto.Pager;
import com.example.product.dto.Product;

@Mapper
public interface ProductDao {
  public int insert(Product product);
  public Product selectByPno(int pno);
  public List<Product> selectByPage(Pager pager);
  public int update(Product product);
  public int delete(int pno);
  public int countAll();
  public int updateHitcount(int pno);
}
