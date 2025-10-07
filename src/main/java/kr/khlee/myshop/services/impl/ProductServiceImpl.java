package kr.khlee.myshop.services.impl;

import kr.khlee.myshop.mappers.ProductMapper;
import kr.khlee.myshop.models.Product;
import kr.khlee.myshop.models.ProductOption;
import kr.khlee.myshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;


    @Override
    public List<Product> getProducts(Product input) throws Exception {
        List<Product> products = null;

        if(input != null && input.getCategoryId() > 0){
            products = productMapper.getProductsByCategory(input);
        } else{
            products = productMapper.getProducts(input);
        }

        return products;
    }

    @Override
    public Product getProductDetail(Product input) throws Exception {
        Product product = productMapper.getProductById(input);

        ProductOption productOption = new ProductOption();
        productOption.setProductId(input.getId());
        String[] optionNames = productMapper.getProductOptionNames(productOption);

        if(optionNames != null && optionNames.length > 0){
            Map<String, List<ProductOption>> options = new HashMap<>();

            for(String optionName : optionNames) {
                ProductOption option = new ProductOption();
                option.setProductId(input.getId());
                option.setName(optionName);

                List<ProductOption> optionList = productMapper.getProductOptionsByName(option);

                options.put(optionName, optionList);
            }

            product.setOptions(options);
        }
        return product;
    }

    @Override
    public int getProductCount(Product input) throws Exception{
        int count = 0;

        if(input != null && input.getCategoryId() > 0){
            count = productMapper.gerProductCountByCategory(input);
        }else {
            count = productMapper.getProductCount(input);
        }

        return count;
    }


}
