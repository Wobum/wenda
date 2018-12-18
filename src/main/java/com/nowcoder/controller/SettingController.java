package com.nowcoder.controller;

import com.nowcoder.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author : Wobum
 * @Date : 2018/12/13 20:34
 * @Software : IntelliJ IDEA
 * @Description:
 **/

@Controller
public class SettingController {

    @Autowired // IOC
            WendaService wendaService;

    @RequestMapping(path = {"/setting"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting() {
        return "setting Ok !" + wendaService.getMessage(1);
    }
}
