package kr.khlee.myshop.services;

import ch.qos.logback.core.encoder.EchoEncoder;
import kr.khlee.myshop.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getProducts(Product input) throws Exception;

    public int getProductCount(Product input) throws Exception;

    public Product getProductDetail(Product input) throws Exception;
}
