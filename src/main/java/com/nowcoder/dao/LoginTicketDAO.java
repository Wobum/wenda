package com.nowcoder.dao;

import com.nowcoder.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @Author : Wobum
 * @Date : 2018/12/27 15:16
 * @Software : IntelliJ IDEA
 * @Description: 连接 login_ticket 表
 **/
@Mapper
public interface LoginTicketDAO {
    // 进行简化，注意加空格
    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = "user_id, expired, status, ticket";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    // 向表中添加一个 login_ticket
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId}, #{expired}, #{status}, #{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(@Param("ticket") String ticket);

    // 更新 ticket 状态
    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
