package kr.khlee.myshop.models;

import lombok.Data;

@Data
public class ProductOption {
    private int id;
    private int productId;
    private String name;
    private String value;
    private String regDate;
    private String editDate;
}
