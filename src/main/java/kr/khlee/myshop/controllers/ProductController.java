package kr.khlee.myshop.controllers;


import kr.khlee.myshop.helpers.PageHelper;
import kr.khlee.myshop.models.Category;
import kr.khlee.myshop.models.Product;
import kr.khlee.myshop.services.CategoryService;
import kr.khlee.myshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping({"/", "/products", "/products/{categoryId}"})
    public String products(Model model,
                           @PathVariable(value="categoryId", required = false) Integer categoryId,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "page", defaultValue = "1") int nowPage) throws Exception{
        List<Category> categories = categoryService.getAllCategories();

        String categoryName = "ALL PRODUCTS";

        if(categoryId != null && categoryId > 0){
            for(Category c : categories){
                if(c.getId() == categoryId){
                    categoryName = c.getName();
                    break;
                }
            }
        }

        Product input = new Product();

        if(categoryId != null && categoryId > 0){
            input.setCategoryId(categoryId);
        }

        if(keyword !=null && !keyword.trim().isEmpty()){
            input.setName(keyword);
        }
        int listCount = 20;
        int groupCount = 5;

        int totalCount = productService.getProductCount(input);

        PageHelper pageHelper = new PageHelper(nowPage, totalCount, listCount, groupCount);

        Product.setOffset(pageHelper.getOffset());
        Product.setListCount(pageHelper.getListCount());

        List<Product> products = productService.getProducts(input);

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageHelper", pageHelper);
        return "products/index";
    }

    @GetMapping({"/products/detail/{id}", "/products/detail/{id}/{categoryId}"})
    public String productDetail(
            Model model,
            @PathVariable(value="id", required = true) int id,
            @PathVariable(value="categoryId", required = false) Integer categoryId) throws Exception{

        List<Category> categories = categoryService.getAllCategories();

        String categoryName = "ALL PRODUCTS";

        if(categoryId != null && categoryId > 0){
            for(Category c : categories){
                if(c.getId() == categoryId){
                    categoryName = c.getName();
                    break;
                }
            }
        }

        Product input = new Product();
        input.setId(id);
        Product product = productService.getProductDetail(input);

        if(product == null){
            throw new NoHandlerFoundException("GET", "/products/detail/" + id + "/" + categoryId, null);
        }

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("categories", categories);
        model.addAttribute("productId", id);
        model.addAttribute("product", product);

        return "products/detail";
    }



}
