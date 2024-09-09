package com.atguigu.wc;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * 无界流实现wordcount
 */

/*
1、 算子之间的传输关系：
        一对一
        重分区

2、 算子 串在一起的条件
        1）  一对一
        2）  并行度相同

3、 关于算子的api:
        1） 全局禁用算子链: env.disableOperatorChaining();
        2） 某个算子不参与链化:  算子A.disableChainning();   算子A不会与 前面 和 后面的 算子 穿着一起
        3） 从某个算子开启新链条:  算子A.startNewChain();  算子A不与  前面串在一起， 从A开始正常链化

 */
public class StreamUnboundedCopy {
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
                        (String value, Collector<String> out) -> {
                            String[] words = value.split(" ");
                            for (String word : words) {
                                out.collect(word);
                            }

                        }
                )
                .returns(Types.STRING)
                .map(word -> Tuple2.of(word, 1))
                // .setParallelism(2)
                .returns(Types.TUPLE(Types.STRING, Types.INT))
                .keyBy(value -> value.f0)
                .sum(1);

        // 4、输出数据
        sum.print();

        // 5、执行
        env.execute();
    }
}
