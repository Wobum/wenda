package com.nowcoder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Wobum
 * @Date : 2018/12/20 15:30
 * @Software : IntelliJ IDEA
 * @Description: 把需要传递给前端的数据整合在一起
 **/
public class ViewObject {
    private Map<String,Object> objs = new HashMap<>();

    public void setObjs(String key, Object value){
        objs.put(key,value);
    }

    public Object get(String key){
        return objs.get(key);
    }
}
