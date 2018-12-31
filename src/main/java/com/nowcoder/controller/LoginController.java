package com.nowcoder.controller;

import com.nowcoder.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author : Wobum
 * @Date : 2018/12/27 14:10
 * @Software : IntelliJ IDEA
 * @Description:
 **/
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    // 定以注册 post 请求的处理，请求会传递两个参数 username 和 password
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      HttpServletResponse response) {
        try {

            Map<String, String> map = userService.register(username, password); // Controller 层调用 Service 层 register 方法
            // 如果 map 中含有 ticket
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket")); // 生成一个新的 cookie
                cookie.setPath("/");
                response.addCookie(cookie); // cookie 下发给客户端
                return "redirect:/"; // 注册成功，返回首页
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return "login";
        }
    }

    // 定以注册页面
    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String reg(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next); // 把 next 埋在模板中
        return "login";
    }

    // 定义登录的 post 请求处理
    @RequestMapping(path = {"/login/"}, method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "next", required = false) String next, // 跳转处理
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme, // 记住我选项
                        HttpServletResponse response) {
        try {
            Map<String, String> map = userService.login(username, password); // 登陆成功，返回 {"ticket":ticket}
            if (map.containsKey("ticket")) { // 登陆成功
                Cookie cookie = new Cookie("ticket", map.get("ticket"));// 创建一个包含 ticket 的 cookie
                cookie.setPath("/");
                response.addCookie(cookie);
                if (StringUtils.isNotBlank(next)) { // 如果 next 不是空，则返回 next
                    return "redirect:" + next;
                }
                return "redirect:/"; // 否则返回首页
            } else { // 登陆失败
                model.addAttribute("msg", map.get("msg"));  // 将失败的信息加入前端页面
                return "login";
            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return "login";
        }

    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String reg(@CookieValue(value = "ticket") String ticket) { // 从 cookie 中读取 ticket
        userService.Logout(ticket); // 登出操作，将 ticket 状态为置位 1
        return "redirect:/"; // 返回首页
    }

}
