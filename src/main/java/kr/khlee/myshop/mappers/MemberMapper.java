package kr.khlee.myshop.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.lang.reflect.Member;

@Mapper
public interface MemerMapper {

    @Select("<script>"
            +"SELECT COUNT(*) FROM members"
            +"<WHERE>"
            +"<if test='userId != null'>user_id = #{userId}</if>"
            +"<if test='email != null'>email = #{email}</if>"
            +"<if test='id != 0'>AND id != #{id}</if>"
            +"</WHRER>"
            +"</script>")

    public int selectCount(Member input);
}
