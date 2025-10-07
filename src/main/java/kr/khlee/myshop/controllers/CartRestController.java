package kr.khlee.myshop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class CartRestController {

    @PostMapping("/api/cart/add")
    public Map<String, Object> addTOCart(
            @RequestParam("productId") Long productId,
            @RequestParam("productName") String productName,
            @RequestParam("options") String[] options){

        log.info("=== 장바구니 담기 요청 수신 ===");
        log.info("상품 ID: {}", productId);
        log.info("상품명: {}", productName);
        for(String option : options){
            log.info("옵션: {}", option);
        }
        return null;
    }

}
