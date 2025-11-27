package kr.khlee.myshop.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Cart {
    private int id;
    private int memberId;
    private int productId;
    private String optionText;
    private int quantity;
    private LocalDateTime regDate;

    private Product product;  // 조인용

    public int getTotalPrice() {
        return (product.getPrice() - (product.getPrice() * product.getDiscount() / 100)) * quantity;
    }

    public int getDiscountedPrice() {
        return product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
    }
}
