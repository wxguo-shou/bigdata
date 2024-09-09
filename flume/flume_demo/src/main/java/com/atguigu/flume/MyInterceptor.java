package com.atguigu.flume;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.security.spec.ECField;
import java.util.List;
import java.util.Map;

/**
 * @author name 婉然从物
 * @create 2023-11-21 19:42
 *
 * 1. 实现flume的拦截器接口
 * 2. 重写四个抽象方法
 * 3. 编写静态内部类 builder
 */
public class MyInterceptor implements Interceptor{

    // 初始化方法
    @Override
    public void initialize() {

    }

    // 处理单个event
    @Override
    public Event intercept(Event event) {
        // 需求： 在event的头信息中添加标记
        // 提供给channel selector 选择发送到不同的channel
        Map<String, String> headers = event.getHeaders();
        String log = new String(event.getBody());

        // 判断log的开头第一个字符
        // 如果是字母发送到channel1  如果是数字发送到channel2
        char c = log.charAt(0);
        if (c >= '0' && c <= '9'){
            // c为数字
            headers.put("type", "number");
        } else if ((c>='A' && c<='Z') || (c>='a' && c<='z')){
            headers.put("type", "letter");
        }
        // 因为头信息属性是一个引用数据类型  直接修改对象即可
        // 也可以不调用set方法
        event.setHeaders(headers);
        return event;
    }

    // 处理多个event
    @Override
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    // 关闭
    @Override
    public void close() {

    }

    public static class Builder implements Interceptor.Builder{

        // 创建一个拦截器对象
        @Override
        public Interceptor build() {
            return new MyInterceptor();
        }

        // 配置方法
        @Override
        public void configure(Context context) {

        }
    }
}
