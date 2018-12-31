package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * @Author : Wobum
 * @Date : 2018/12/20 15:23
 * @Software : IntelliJ IDEA
 * @Description: 用户登录，注册，添加 ticket 和登出逻辑
 **/
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    // 用户注册逻辑
    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>(); // 创建一个 map 变量用来存储结果

        // 如果 username 为空
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        // 如果 password 为空
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        // 用户名和密码都符合规则后，判断用户名是否已经被注册
        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("msg", "用户已存在");
            return map;
        }

        // 判断完成后，生成用户，加入数据库
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5)); //
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password + user.getSalt())); // MD5 加密
        userDAO.addUser(user);

        // 给用户添加 ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    // 用户登录逻辑，登录成功，返回 {"ticket":ticket} 信息，登录失败，返回 {"msg",错误信息}
    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<>();

        // 如果 username 为空
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        // 如果 password 为空
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        // 判断用户名是否已经被注册
        User user = userDAO.selectByName(username);
        if (user == null) {
            map.put("msg", "用户不存在");
            return map;
        }

        // 判断用户密码是否正确
        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "密码错误");
            return map;
        }

        // 给用户发送 ticket
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    // 给对应的 userId 发送 ticket，返回对应的 ticket
    public String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600 * 24 * 100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        // 在数据库中添加这个 userId 对应的 ticket
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();

    }

    // 用户登出
    public void Logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }

}
