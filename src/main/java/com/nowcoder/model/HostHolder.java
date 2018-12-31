package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * @Author : Wobum
 * @Date : 2018/12/29 11:05
 * @Software : IntelliJ IDEA
 * @Description:
 **/
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>(); // 每个线程都有一个变量,但可以通过公共借口访问

    // 根据当前线程返回对应 User
    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
