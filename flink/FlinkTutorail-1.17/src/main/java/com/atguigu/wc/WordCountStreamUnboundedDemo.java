package com.atguigu.wc;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * 无界流实现wordcount
 */
public class WordCountStreamUnboundedDemo {
    public static void main(String[] args) throws Exception {
        // 1、创建执行环境
        // StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // IEDA运行时，也可以看到webui, 一般用于本地测试
        // 需要引入一个依赖 flink-runtime-web
        // 在idea运行， 不指定并行度 默认就是 电脑的 线程数
        // 并行度 优先级  ：  代码算子 > 代码env >  提交时指定 >  配置文件
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setParallelism(2);

        // 2、读取数据: socket
        DataStreamSource<String> socketDS = env.socketTextStream("hadoop102", 7777);

        // 3、处理数据： 切分、转换、分组、聚合
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = socketDS
                .flatMap(
                        (String value, Collector<Tuple2<String, Integer>> out) -> {
                            String[] words = value.split(" ");
                            for (String word : words) {
                                out.collect(Tuple2.of(word, 1));
                            }

                        }
                )
                // .setParallelism(2)
                .returns(Types.TUPLE(Types.STRING, Types.INT))
//                .returns(new TypeHint<Tuple2<String, Integer>>() {})
                .keyBy(value -> value.f0)
                .sum(1);

        // 4、输出数据
        sum.print();

        // 5、执行
        env.execute();
    }
}
