package kr.khlee.myshop;

import kr.khlee.myshop.mappers.ProductMapper;
import kr.khlee.myshop.models.Product;
import kr.khlee.myshop.models.ProductOption;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class ProductMapperTests {
    @Autowired
    private ProductMapper productMapper;

    @Test
    void testGetProducts() {
        List<Product> products = productMapper.getProducts(null);

        for( Product product : products ) {
            log.info("categoryId: {}, Product ID: {}, Name: {}, Price: {}, Discount: {}, Summary: {}, ImageURL: {}",
                    product.getCategoryId(),
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDiscount(),
                    product.getSummary(),
                    product.getImageUrl());
        }
    }

    @Test
    void testGetProductsByCategory() {
        Product input = new Product();
        input.setCategoryId(10);

        List<Product> products = productMapper.getProductsByCategory(input);

        for(Product product : products ) {
            log.info("categoryId: {},Product Id: {}, Name: {}, Price: {}, Discount: {}, Summary: [}, ImageURL: {}",
                    product.getCategoryId(),
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDiscount(),
                    product.getSummary(),
                    product.getImageUrl());
        }
    }

    @Test
    void getProductById() {
        Product input = new Product();
        input.setId(11023);

        Product product = productMapper.getProductById(input);

        log.info("productID: {}, Name : {}, Price: {}, Discount: {}, Summary: {}, Image URL: {}",
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDiscount(),
                product.getSummary(),
                product.getImageUrl());
    }

    @Test
    void testGetProductByNames() {
        ProductOption input = new ProductOption();
        input.setProductId(11023);

        String[] output = productMapper.getProductOptionNames(input);

        for(String optionName : output) {
            log.info("Option Name: {}", optionName);
        }
    }

    @Test
    void testGetProductOptionByName() {
        ProductOption input = new ProductOption();
        input.setProductId(11023);
        input.setName("사이즈");

        List<ProductOption> options = productMapper.getProductOptionsByName(input);

        for(ProductOption option : options ) {
            log.info("Option ID: {}, Name: {}, Value: {}",
                    option.getId(),
                    option.getName(),
                    option.getValue());
        }
    }
}
