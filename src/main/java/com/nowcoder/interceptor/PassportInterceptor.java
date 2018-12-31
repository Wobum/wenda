package com.nowcoder.interceptor;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.HostHolder;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author : Wobum
 * @Date : 2018/12/29 10:55
 * @Software : IntelliJ IDEA
 * @Description: 查看请求中 cookie 是否含有 ticket，并验证是否有效
 **/
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    HostHolder hostHolder;

    @Override // 请求开始之前调用这个函数
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*if (2 > 1){
            return  false; 如果返回 fasle，则不会再进行下面的操作。
        }*/

        String ticket = null;
        if (request.getCookies() != null){ // 是否有 cookie
            for (Cookie cookie : request.getCookies()){ // 遍历 cookie
                if (cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket != null){ // 判断 ticket
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0){
                return true;
            }
            User user = userDAO.selectById(loginTicket.getUserId()); // 取出 User
            hostHolder.setUser(user); // 注入 hostHolder，后面所有的请求都可以访问
        }

        return true;
    }

    @Override // 页面渲染之前
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){ // view 指静态中的各种模板，model 指传入模板中的变量
            modelAndView.addObject("user", hostHolder.getUser()); // 将 User 放入模板中
        }
    }

    @Override // 页面渲染完成之后调用
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear(); // 渲染完成后清除用户，否则 ThreadLocal 中的用户会越来越多。
    }
}
