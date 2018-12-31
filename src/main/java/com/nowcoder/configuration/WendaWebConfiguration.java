package com.nowcoder.configuration;

import com.nowcoder.interceptor.LoginRequredInterceptor;
import com.nowcoder.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * @Author : Wobum
 * @Date : 2018/12/29 11:15
 * @Software : IntelliJ IDEA
 * @Description: 配置 Interceptor
 **/
@Component
public class WendaWebConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequredInterceptor loginRequredInterceptor;


    // 加入拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor); // 在整个链路中注册入拦截器
        registry.addInterceptor(loginRequredInterceptor).addPathPatterns("/user/*"); // 当访问 user 页面时调用这个拦截器
        super.addInterceptors(registry);
    }
}
