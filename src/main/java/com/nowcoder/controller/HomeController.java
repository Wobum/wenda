package com.nowcoder.controller;

import com.nowcoder.model.Question;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Wobum
 * @Date : 2018/12/18 16:18
 * @Software : IntelliJ IDEA
 * @Description:
 **/
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/user/{userId}"}, method = RequestMethod.GET)
    public String userindex(Model model, @PathVariable(value = "userId") int userId) {
        model.addAttribute("vos", getLatestQuestions(userId, 0, 10));
        return "index";
    }

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("vos", getLatestQuestions(0, 0, 10)); // vos 传递给模板
        return "index";
    }

    @RequestMapping(path = {"/test"}, method = RequestMethod.GET)
    public String test() {
        return "login";
    }

    private List<ViewObject> getLatestQuestions(int userId, int offest, int limit) {
        List<Question> questionList = questionService.getLatestQuestions(userId, offest, limit); // 获取最新的问题列表
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) { // 遍历问题列表
            ViewObject viewObject = new ViewObject();
            viewObject.setObjs("question", question); // 把每一个问题加入到 map 中
            viewObject.setObjs("user", userService.getUser(question.getUserId())); // 调用 Service 方法查询数据库，把每个问题对应的 user 加入到 map 中
            vos.add(viewObject); // 把这个 map 加入到队列中
        }
        return vos; // vos 每一项都是一个 map ，map 中有两项，question 项和 user 项
    }
}
