package com.example.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.product.dao.ProductDao;
import com.example.product.dto.Pager;
import com.example.product.dto.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    // 상품 생성
    public void create(Product product) {
        productDao.insert(product);
    }

    // 상품 조회
    public Product getProductInfo(int pno) {
        productDao.updateHitcount(pno);
        Product product = productDao.selectByPno(pno);
        return product;
    }

    // 페이징 객체 전체 개수 조회하기
    public int getTotalRows() {
        int totalRows = productDao.countAll();
        return totalRows;
    }

    // 페이징 객체 생성
    public List<Product> getListByPage(Pager pager) {
        List<Product> list = productDao.selectByPage(pager);
        return list;
    }

    // 상품 변경
    public Product update(Product product) {
        Product dbProduct = productDao.selectByPno(product.getPno()); // no값을 받아오기
        if (dbProduct == null) {
            return null;
        } else {
            if (StringUtils.hasText(product.getPname())) {
                dbProduct.setPname(product.getPname());
            }
            if (StringUtils.hasText(product.getPcontent())) {
                dbProduct.setPcontent(product.getPcontent());
            }
            if (product.getPprice() != null) { // hasText는 str를 매개변수로 받아 사용 불가
                dbProduct.setPprice(product.getPprice());
            }
        }
        productDao.update(dbProduct);
        dbProduct = productDao.selectByPno(product.getPno());
        return dbProduct;
    }

     // 클라이언트 응답 결과
    public enum RemoveResult {
        SUCCESS, FAIL
    }

    // 상품 삭제 
    public RemoveResult remove(int pno) {
        int rows = productDao.delete(pno);
        if (rows == 0) {
            return RemoveResult.FAIL;

        } else {
            return RemoveResult.SUCCESS;
        }
    }
}
