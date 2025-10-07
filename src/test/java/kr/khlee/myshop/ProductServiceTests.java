package kr.khlee.myshop;

import kr.khlee.myshop.models.Product;
import kr.khlee.myshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Test
    void testGetProducts() throws Exception {
        List<Product> productList = productService.getProducts(null);

        for(Product product : productList){
            log.info("Product ID: {}, Name: {}, Price: {}, Discount: {}, Summary: {}, ImageURL: {}",
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDiscount(),
                    product.getSummary(),
                    product.getImageUrl());
        }
    }

    @Test
    void testGetProductDetail() throws Exception {
        Product input = new Product();
        input.setId(11023);

        Product productDetail = productService.getProductDetail(input);

        log.info("Product Detail: ID: {}, Name: {}, Price: {}, Discount: {}, ImageURL: {}, options: {}",
                productDetail.getId(),
                productDetail.getName(),
                productDetail.getPrice(),
                productDetail.getDiscount(),
                productDetail.getImageUrl(),
                productDetail.getOptions());

    }

}
