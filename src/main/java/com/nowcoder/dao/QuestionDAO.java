package com.nowcoder.dao;

import com.nowcoder.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.net.IDN;
import java.util.List;

/**
 * @Author : Wobum
 * @Date : 2018/12/18 14:20
 * @Software : IntelliJ IDEA
 * @Description: 连接 Question 表
 **/

@Mapper
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = "title, content, user_id, created_date, comment_count";
    String SELECT_FIELDS = "id " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, " (", INSERT_FIELDS, ") values(#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    void insertQuestion(Question question);

    // 具体执行方法为 QuestionDAO.xml 文件
    List<Question> selectLatestQuestions(@Param("userId") int userId,@Param("offest") int offest,
                                         @Param("limit") int limit);
}
