package com.atguigu.combine;

import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

public class ConnectDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

//        DataStreamSource<Integer> source1 = env.fromElements(1, 2, 3);
//        DataStreamSource<String> source2 = env.fromElements("111", "222", "333");

        SingleOutputStreamOperator<Integer> source1 = env.socketTextStream("hadoop102", 7777)
                .map(r -> Integer.valueOf(r));
        DataStreamSource<String> source2 = env.socketTextStream("hadoop102", 8888);

        ConnectedStreams<Integer, String> connect = source1.connect(source2);

        SingleOutputStreamOperator<String> result = connect.map(new CoMapFunction<Integer, String, String>() {
            @Override
            public String map1(Integer integer) throws Exception {
                return "来源于数字流" + integer.toString();
            }

            @Override
            public String map2(String s) throws Exception {
                return "来源于字母流" + s;
            }
        });

        result.print();

        env.execute();
    }
}
