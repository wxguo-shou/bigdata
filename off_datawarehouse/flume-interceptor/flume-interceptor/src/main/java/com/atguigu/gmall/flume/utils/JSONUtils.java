package com.atguigu.gmall.flume.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author name 婉然从物
 * @create 2023-11-26 16:08
 */
public class JSONUtils {
    public static boolean isJSONValidate(String log) {
        try {
            JSONObject.parseObject(log);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(JSONObject.parseObject("{id: 1}"));
//    }
}
