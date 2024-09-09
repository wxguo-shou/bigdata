package com.atguigu.gmall.flume.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author name 婉然从物
 * @create 2023-12-05 9:12
 */
public class TimestampAndTableNameInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        // 1. 获取 header 和 body 当中的数据
        Map<String, String> headers = event.getHeaders();
        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);

        // 2. 解析log当中的 ts 和 table 字段
        JSONObject jsonObject = JSONObject.parseObject(log);
        String table = jsonObject.getString("table");
        String ts = jsonObject.getString("ts");

        // 3. 把 ts 和 table 放到header当中的tableName和 timestamp
        headers.put("tableName", table);
        headers.put("timestamp", ts + "000");
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new TimestampAndTableNameInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
