package kr.khlee.myshop.services;

import kr.khlee.myshop.models.Category;

import java.util.List;

public interface CategoryService {
    /**
     *
     * @return
     * @throws Exception
     */
    public List<Category> getAllCategories() throws Exception;
}
