package kr.khlee.myshop.services.impl;


import kr.khlee.myshop.exceptions.ServiceNoResultException;
import kr.khlee.myshop.mappers.CategoryMapper;
import kr.khlee.myshop.models.Category;
import kr.khlee.myshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() throws Exception{
        Category input = new Category();
        input.setParentId(0);

        List<Category> output = categoryMapper.getAllCategories(input);

        if(output == null || output.isEmpty()){
            throw new ServiceNoResultException("조회된 Category 데이터가 없습니다.");
        }

        for(Category c : output){
            Category childInput = new Category();
            childInput.setParentId(c.getId());

            List<Category> childCategories = categoryMapper.getAllCategories(childInput);
            c.setSubCategories(childCategories);
        }

        return output;
    }
}
