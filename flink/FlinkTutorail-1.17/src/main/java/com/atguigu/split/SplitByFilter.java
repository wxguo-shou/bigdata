package com.atguigu.split;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SplitByFilter {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<String> socketDS = env.socketTextStream("hadoop102", 7777);

        // filter实现分流
        socketDS.filter(value -> Integer.parseInt(value) % 2 == 0).print("偶数流");
        socketDS.filter(value -> Integer.parseInt(value) % 2 == 1).print("奇数流");

        env.execute();
    }
}
