package kr.khlee.myshop;

import kr.khlee.myshop.models.Category;
import kr.khlee.myshop.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;


    @Test
    void testGetAllCategories(){
        try{
            List<Category> categories = categoryService.getAllCategories();

            for(Category category : categories){
                log.info("CategoryId: {}, Name: {}", category.getId(), category.getName());

                if(category.getSubCategories() != null){
                    for(Category subCategory : category.getSubCategories()){
                        log.info(" --> SubCategoryId: {}, Name: {}", subCategory.getId(), subCategory.getName());
                    }
                }
            }
        }catch(Exception e){
            log.error("Error retrieving categories", e);
        }
    }
}
