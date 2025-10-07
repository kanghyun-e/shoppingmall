package kr.khlee.myshop.models;

import lombok.Data;

import java.util.List;

@Data
public class Category {
    private int id;
    private String name;
    private int parentId;
    private int sort;
    private String regDate;
    private String editDate;

    private List<Category> subCategories;
}
