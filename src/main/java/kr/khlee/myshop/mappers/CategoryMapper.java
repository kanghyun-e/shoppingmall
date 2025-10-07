package kr.khlee.myshop.mappers;

import kr.khlee.myshop.models.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("<script>" +
            "SELECT id, name, parent_id, sort, reg_date, edit_date FROM categories " +
            "<where>" +
            //parentId가 0인 경우에는 1depth 카테고리만 조회
            "<if test='parentId == 0'> parent_id is null</if>" +
            "<if test='parentId != 0'> parent_id = #{parentId}</if>" +
            "</where>" +
            "ORDER BY sort ASC" +
            "</script>")
    @Results(id = "resultMap")
    public List<Category> getAllCategories(Category input);

}
