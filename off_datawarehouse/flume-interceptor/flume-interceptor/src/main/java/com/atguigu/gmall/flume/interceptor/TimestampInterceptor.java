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
 * @create 2023-12-02 16:51
 */
public class TimestampInterceptor implements Interceptor {
    @Override
    public void initialize() {

    }

    @Override
    public Event intercept(Event event) {
        // 1. 获取header 和 body 当中的数据
        Map<String, String> headers = event.getHeaders();
        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);

        // 2. 解析 log(json) 的ts时间戳字段
        JSONObject jsonObject = JSONObject.parseObject(log);
        String ts = jsonObject.getString("ts");

        // 3. 把解析出来的ts put到header头当中的timestamp
        headers.put("timestamp", ts);
        return event;
    }

    @Override
    public List<Event> intercept(List<Event> list) {
        for (Event event : list) {
            intercept(event);
        }
        return list;
    }

    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new TimestampInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
