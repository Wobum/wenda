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
 * @Date : 2018/12/29 11:46
 * @Software : IntelliJ IDEA
 * @Description:
 **/
@Component
public class LoginRequredInterceptor implements HandlerInterceptor {

    @Autowired
    HostHolder hostHolder;

    @Override // 请求开始之前调用这个函数
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hostHolder.getUser() == null){ // 如果没有登录，直接跳转登录页面，并把当前页面通过参数 next 带过去
            response.sendRedirect("/reglogin?next=" + request.getRequestURL());
        }
        return true;
    }

    @Override // 页面渲染之前
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override // 页面渲染完成之后调用
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
