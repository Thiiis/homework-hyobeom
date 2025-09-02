package com.example.product.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.product.dto.Pager;
import com.example.product.dto.Product;
import com.example.product.service.ProductService;
import com.example.product.service.ProductService.RemoveResult;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 생성
   @PostMapping("/create")
    public Product productCreate(Product product) throws IOException { 
        log.info(product.toString());

        MultipartFile mf = product.getPattach();
		if(mf != null && !mf.isEmpty()) {
			product.setPattachoname(mf.getOriginalFilename());
			product.setPattachtype(mf.getContentType());
			product.setPattachdata(mf.getBytes());
		}
        
        productService.create(product);

        Product dbProduct = productService.getProductInfo(product.getPno());
        log.info(dbProduct.toString());
        return dbProduct;
    }

    // text 요청 및 응답 (다운로드)
    @GetMapping("/product-detail")
    public Product productDetail(@RequestParam("pno") int pno) {
        Product product = productService.getProductInfo(pno);
        return product;
    }

    // 데이터만 따로 요청 및 응답(속도개선을 위한) (다운로드)
    @GetMapping("/product-attach-download")
    public void productAttachDownload(@RequestParam("pno") int pno, HttpServletResponse response) throws Exception {
        
        Product product = productService.getProductInfo(pno);

        String fileName = product.getPattachoname();

        // 응답 헤더에 Content-Type 추가
        response.setContentType(product.getPattachtype());

        // 본문의 내용을 파일로 저장할 수 있도록 헤더 추가
        String encodedFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName +"\"" );

        // 응답 본문으로 데이터를 출력하는 스트림 (성능향상)
        OutputStream os = response.getOutputStream(); 
        BufferedOutputStream bos = new BufferedOutputStream(os);

        //응답 본문에 파일 데이터 출력 
        bos.write(product.getPattachdata());
        bos.flush();
        bos.close();
    }

    // 상품 조회
    @GetMapping("/product-info")
    public Product productInfo(@RequestParam(value = "pno", defaultValue = "1") int pno) {
        Product product = productService.getProductInfo(pno);
        return product;
    }

     // 페이징 조회
    @GetMapping("/product-list")
    public Map<String, Object> boardList(@RequestParam(value="pageNo", defaultValue = "1") int pageNo) {
        log.info("pageNo: " + pageNo);

        int totalRows = productService.getTotalRows();
        Pager pager = new Pager(10, 10, totalRows, pageNo);

        List<Product> list = productService.getListByPage(pager);

        Map<String, Object> map = new HashMap<>();
        map.put("pager", pager);
        map.put("product", list);
        
        return map;
    }

    // 상품 변경
    @PutMapping("/product-update")
    public Product productUpdate(Product product) throws Exception {
        
        MultipartFile mf = product.getPattach();
        if(mf != null & !mf.isEmpty()) {
            product.setPattachoname(mf.getOriginalFilename());
            product.setPattachtype(mf.getContentType());
            product.setPattachdata(mf.getBytes());
        }
        productService.update(product);
        Product dbProduct = productService.getProductInfo(product.getPno());
        
        return dbProduct;
    }

    // 상품 삭제
    @DeleteMapping("/remove/{pno}")
    public String remove(@PathVariable("pno") int pno) {
        RemoveResult removeResult = productService.remove(pno);
        JSONObject jsonObject = new JSONObject();
        if (removeResult == RemoveResult.SUCCESS) {
            jsonObject.put("result", "success");
        } else {
            jsonObject.put("result", "fail");
        }
        return jsonObject.toString();// {"result":"SUCCESS"}
    }

     // 반복 생성
    @GetMapping("/temp")
    public String temp() {
        for(int i =1; i<= 1000; i++) {
            Product product = new Product();
            product.setPname("제품명" + i);
            product.setPcontent("판매가격" + i);
            product.setPprice(i);
            productService.create(product);
        }
        return "1000개";
    }

    
}