package kr.khlee.myshop.mappers;

import kr.khlee.myshop.models.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    /** 장바구니 목록 조회 (JOIN 포함) */
    @Select("""
        SELECT c.id, c.member_id, c.product_id, c.option_text, c.quantity, c.reg_date,
               p.name AS product_name,
               p.summary AS product_summary,
               p.price AS product_price,
               p.discount AS product_discount,
               p.image_url AS product_image_url
        FROM cart AS c
        INNER JOIN products AS p ON p.id = c.product_id
        WHERE c.member_id = #{memberId}
        ORDER BY c.id DESC
        """)
    @Results(id="cartResultMap", value = {
            @Result(property="id", column="id"),
            @Result(property="memberId", column="member_id"),
            @Result(property="productId", column="product_id"),
            @Result(property="optionText", column="option_text"),
            @Result(property="quantity", column="quantity"),
            @Result(property="regDate", column="reg_date"),

            @Result(property="product.id", column="product_id"),
            @Result(property="product.name", column="product_name"),
            @Result(property="product.summary", column="product_summary"),
            @Result(property="product.price", column="product_price"),
            @Result(property="product.discount", column="product_discount"),
            @Result(property="product.imageUrl", column="product_image_url"),
    })
    List<Cart> selectList(Cart input);


    /** 특정 아이템 조회 (JOIN 적용 버전) */
    @Select("""
    SELECT c.id, c.member_id, c.product_id, c.option_text, c.quantity, c.reg_date,
           p.name AS product_name,
           p.summary AS product_summary,
           p.price AS product_price,
           p.discount AS product_discount,
           p.image_url AS product_image_url
    FROM cart AS c
    INNER JOIN products AS p ON p.id = c.product_id
    WHERE c.id = #{id}
    """)
    @ResultMap("cartResultMap")
    Cart selectItem(Cart input);



    /** 추가 */
    @Insert("""
        INSERT INTO cart (member_id, product_id, option_text, quantity)
        VALUES (#{memberId}, #{productId}, #{optionText}, #{quantity})
        """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Cart input);


    /** 수정 (수량만 변경) */
    @Update("""
    UPDATE cart
    SET quantity = quantity + #{quantity}
    WHERE id = #{id}
""")
    void update(Cart input);


    /** 삭제 */
    @Delete("""
        DELETE FROM cart WHERE id = #{id}
        """)
    void delete(Cart input);


    /** 카트 전체 개수 */
    @Select("""
        SELECT COUNT(*) FROM cart WHERE member_id = #{memberId}
        """)
    int count(Cart input);
}
