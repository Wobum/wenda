package com.nowcoder.dao;

import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

import java.lang.invoke.VarHandle;


/**
 * @Author : Wobum
 * @Date : 2018/12/17 15:29
 * @Software : IntelliJ IDEA
 * @Description:
 **/

@Mapper  //通过 Mapper 指定这个是与 mybatis 关联的
public interface UserDAO {
    // 进行简化，注意加空格
    String TABLE_NAME = " User ";
    String INSERT_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    // 插入一条数据，通过 @Insert 注解，values 是通过 #{} 形式直接从 user 中读取
    @Insert({"insert into", TABLE_NAME, " ( ", INSERT_FIELDS, " )values(#{name}, #{password}, #{salt}, #{headUrl}) "})
    void addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    User selectById(@Param("id")int id);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePasswordByID(@Param("id")int id, @Param("password") String password);

    @Delete({"delete from ", TABLE_NAME, "where id=#{id}"})
    void deleteById(@Param("id")int id);
}

