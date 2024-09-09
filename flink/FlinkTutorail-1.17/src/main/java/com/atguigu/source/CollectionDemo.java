package com.atguigu.source;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

public class CollectionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // TODO 从集合读取数据
        DataStreamSource<Integer> source = env
                .fromElements(1, 22, 33);  // 直接填写元素
//                .fromCollection(Arrays.asList(1, 22, 33));   //  从集合读取

        source.print();

        env.execute();
    }
}
