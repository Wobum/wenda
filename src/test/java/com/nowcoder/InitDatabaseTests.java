package com.nowcoder;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @Author : Wobum
 * @Date : 2018/12/17 16:08
 * @Software : IntelliJ IDEA
 * @Description: 测试数据库是否成功
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/init-schema.sql") // 每次跑测试用例时，都会运行这个 sql 程序。
public class InitDatabaseTests {
    @Autowired // IOC
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Test
    public void initDatabase() {
        Random random = new Random();

        for (int i = 0; i < 11; ++i) {
            User user = new User();

            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("wang" + i);
            user.setSalt(String.valueOf(random.nextInt(1000)));

            userDAO.addUser(user); // 将 user 加入数据库

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * i);
            question.setCreatedDate(date);
            question.setUserId(i + 1);
            question.setUserId(i + 1);
            question.setTitle(String.format("这是几  %d ", i));
            question.setContent(String.format("宝宝健康的房价款腊八节快乐 %d", i));

            questionDAO.insertQuestion(question);
        }
        userDAO.updatePasswordByID(1,"xxxx");
        System.out.println(userDAO.selectById(1).getPassword());
        List<Question> list = questionDAO.selectLatestQuestions(0,0,10);
    }

}