package com.nowcoder.service;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : Wobum
 * @Date : 2018/12/20 15:23
 * @Software : IntelliJ IDEA
 * @Description:
 **/
@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    public List<Question> getLatestQuestions(int userId, int offest, int limit) {
        return questionDAO.selectLatestQuestions(userId, offest, limit);
    }
}
