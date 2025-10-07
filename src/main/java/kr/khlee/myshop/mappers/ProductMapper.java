package kr.khlee.myshop.mappers;

import kr.khlee.myshop.models.Product;
import kr.khlee.myshop.models.ProductOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("<script>" +
            "SELECT id, name, price, discount, summary, image_url FROM products " +
            "<where>" +
            "<if test='name != null and name != \"\"'> name LIKE concat('%', #{name}, '%')</if>" +
            "</where>"
            + "ORDER BY id DESC "
            + "LIMIT 0, 12"
            + "</script>"
            )
    @Results(id = "resultMap")
    public List<Product> getProducts(Product input);

    @Select("<script>"
            + "SELECT p.id, p.name, p.price, p.discount, p.summary, p.image_url, "
            + "p.product_url, p.reg_date, p.edit_date, "
            + "c.id AS category_id, c.name AS category_name "
            + "FROM products AS p " +
            "INNER JOIN product_categories AS pc ON p.id = pc.product_id " +
            "INNER JOIN categories AS c ON pc.category_id = c.id " +
            "<where>" +
            "<if test = 'categoryId > 0'> AND pc.category_id = #{categoryId} </if>" +
            "<if test = 'name != null and name != \"\"'> AND p.name LIKE concat('%', #{name}, '%')</if>" +
            "</where>" +
            "ORDER BY id DESC " +
            "<if test='listCount > 0'> LIMIT #{offset}, #{listCount}</if>" +
            "</script>")
    @Results(id = "getProductsByCategory")
    public List<Product> getProductsByCategory(Product input);

    @Select("<script>" +
            "SELECT id, name, price, discount, summary, image_url, " +
            "delivery_info, md_comment, product_url, content, " +
            "reg_date, edit_date " +
            "FROM products " +
            "<where>" +
            "id = #{id} " +
            "</where>" +
            "</script>")
    @Results(id = "getProductById")
    public Product getProductById(Product input);

    @Select("<script>" +
            "SELECT name " +
            "FROM product_options " +
            "<where>" +
            "product_id = #{productId}" +
            "</where>" +
            "GROUP BY name " +
            "ORDER BY name ASC" +
            "</script>")
    @Results(id = "getProductOption")
    public String[] getProductOptionNames(ProductOption input);

    @Select("<script> " +
            "SELECT id, product_id, name, value, reg_date, edit_date " +
            "FROM product_options " +
            "<where>" +
            "product_id = #{productId} " +
            "AND name = #{name}" +
            "</where>" +
            "ORDER BY value ASC" +
            "</script>")
    @Results(id = "getProductOptionsByName")
    public List<ProductOption> getProductOptionsByName(ProductOption input);

    @Select("<script>" +
            "SELECT COUNT(*) FROM products " +
            "<where>" +
            "<if test='name != null and name != \"\"'> name LIKE concat('%',#{name}, '%')</if>" +
            "</where>" +
            "</script>")
    @Results(id = "getProductCount")
    public int getProductCount(Product input);

    @Select("<script>" +
            "SELECT COUNT(*) " +
            "FROM products AS p " +
            "INNER JOIN product_categories AS pc ON p.id = pc.product_id " +
            "INNER JOIN categories AS c ON pc.category_id = c.id " +
            "<where>" +
            "<if test = 'categoryId > 0'> AND pc.category_id = #{categoryId}</if>" +
            "<if test = 'name != null and name != \"\"'> AND p.name LIKE concat ('%', #{name}, '%')</if>" +
            "</where>" +
            "</script>")
    @Results(id = "getProductCountByCategory")
    public int gerProductCountByCategory(Product input);
}
