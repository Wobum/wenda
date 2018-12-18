package com.nowcoder.service;

import org.springframework.stereotype.Service;

/**
 * @Author : Wobum
 * @Date : 2018/12/13 20:31
 * @Software : IntelliJ IDEA
 * @Description:
 **/

@Service
public class WendaService {
    public String getMessage(int userId){
        return "Hello Message: " + String.valueOf(userId);
    }
}
