package com.nowcoder.dao;

import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;


/**
 * @Author : Wobum
 * @Date : 2018/12/17 15:29
 * @Software : IntelliJ IDEA
 * @Description:
 **/

@Mapper
public interface UserDAO {
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "( " , INSERT_FIELDS, ") values(#{name}, #{password}, #{salt}, #{headUrl})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Update({"update ", TABLE_NAME, " set password=#{password} wheer id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete form ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
