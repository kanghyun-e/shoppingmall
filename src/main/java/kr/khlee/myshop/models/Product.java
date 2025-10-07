package kr.khlee.myshop.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
public class Product {
    private int id;
    private String name;
    private int price;
    private int discount;
    private String summary;
    private String imageUrl;
    private String deliveryInfo;
    private String mdComment;
    private String productUrl;
    private String content;
    private String regDate;
    private String editDate;

    private int categoryId;
    private String categoryName;


    private Map<String, List<ProductOption>> options;

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}
