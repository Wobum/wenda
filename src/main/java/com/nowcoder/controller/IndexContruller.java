package com.nowcoder.controller;

import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.User;
import com.nowcoder.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author : Wobum
 * @Date : 2018/12/13 11:25
 * @Software : IntelliJ IDEA
 * @Description:
 **/

@Controller
public class IndexContruller {
    @Autowired
    public WendaService wendaService;
    UserDAO userDAO;

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    @ResponseBody
    public String index(HttpSession httpSession) {
        return wendaService.getMessage(2) + "Hello World! " + httpSession.getAttribute("msg");
    }

    /*
    @pathVariable() 解析传入 url 中被 {} 括起来的参数，默认按顺序解析，也可以在 pathVariable 中通过 value = 加入参数名字解析
    @RequestParam() 解析传入 url 中请求的参数，默认按顺序解析，也可以在 RequestParam 中通过 value = 加入参数名字解析
    @ResponseBody 返回不调用 templete 时使用。
     */
    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable(value = "userId") int groupId,
                          @PathVariable(value = "groupId") int userId,
                          @RequestParam(value = "key") int key,
                          @RequestParam(value = "type") int type
    ) {
        return String.format("Profile Page of %d + %d + %d + %d", groupId, userId, type, key) + " Hello world";
    }

    /*
    Model 是 SpringMVC 里面 UI 的框架，它会把各种数据传递给模板中
     */
    @RequestMapping(path = {"/vm"}, method = RequestMethod.GET)
    public String templete(Model model) {
        model.addAttribute("value1", "first");
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);

        Map<String, String> maps = new HashMap<>();
        for (int i = 0; i < 4; i++)
            maps.put(String.valueOf(i), String.valueOf(i * i));
        model.addAttribute("maps", maps);

        User user = new User("Wobum");
        model.addAttribute("user", user);
        return "home";
    }

    @RequestMapping(path = {"/request"}, method = RequestMethod.GET)
    @ResponseBody
    public String request(Model model, HttpSession session,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          HttpStatus status,
                          @CookieValue("JSESSIONID") String SessionId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JSESSIONID" + SessionId + "<br>");
        stringBuilder.append(request.getRequestURI() + "<br>");
        stringBuilder.append(request.getPathInfo() + "<br>");
        stringBuilder.append(request.getHeaderNames() + "<br>");
        stringBuilder.append(request.getQueryString() + "<br>");
        stringBuilder.append(request.getMethod() + "<br>");
        stringBuilder.append(request.getCookies() + "<br>");
        stringBuilder.append(response.getStatus() + "<br>");
        stringBuilder.append(response.getHeaderNames() + "<br>");
        stringBuilder.append(session.getId() + "<br>");
        stringBuilder.append(session.getCreationTime() + "<br>");
        stringBuilder.append(session.getAttributeNames() + "<br>");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            stringBuilder.append(name + " :" + request.getHeader(name) + "<br>");
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                stringBuilder.append("Cookie:" + cookie.getName() + "value: " + cookie.getValue());
            }
        }

        response.addHeader("nowcoder", "hello");
        response.addCookie(new Cookie("username", "nowcoder"));

        return stringBuilder.toString();
    }

    /*
    重定向，可以通过设置 httpSession.setAttribute 来在不同页面之间进行通信
     */
    @RequestMapping(path = {"/redirect/{code}"}, method = RequestMethod.GET)
    public RedirectView redirect(@PathVariable(value = "code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg", "Jump from redirect!");
        RedirectView redirectView = new RedirectView("/", true);
        if (code == 301) {
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY); //设置 statusCode，301 表示永久跳转
        } else if (code == 302) {
            redirectView.setStatusCode(HttpStatus.FOUND);   // 302 表示临时跳转
        }
        return redirectView;
    }

    @RequestMapping(path = {"/admin"}, method = RequestMethod.GET)
    @ResponseBody
    public String admin(@RequestParam(value = "key") String key) {
        if (key.equals("admin")) {
            return "hello admin";
        }
        throw new IllegalArgumentException("参数错误");
    }

    /*
    进行异常的统一处理，用 @ExceptionHandler
     */
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "error: " + e.getMessage();
    }

}
