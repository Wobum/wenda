package com.nowcoder.model;

import java.util.Date;

/**
 * @Author : Wobum
 * @Date : 2018/12/27 15:14
 * @Software : IntelliJ IDEA
 * @Description: 用于将数据库中读取的 login_ticket 进行格式化
 **/
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private int status;
    private String ticket;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public int getStatus() {
        return status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
