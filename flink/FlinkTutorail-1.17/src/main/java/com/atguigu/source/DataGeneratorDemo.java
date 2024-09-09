package com.atguigu.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.connector.source.util.ratelimit.RateLimiterStrategy;
import org.apache.flink.connector.datagen.source.DataGeneratorSource;
import org.apache.flink.connector.datagen.source.GeneratorFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class DataGeneratorDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 如果有n 个并行度，最大值设为a
        // 将数值 均分成 n份， a/n ，比如，最大100，并行度2，每个并行度生成50个
        // 其中一个是0-49，另一个50-99
        env.setParallelism(1);

        /**
         * 数据生成器source，四个参数:
         *      第一个:GeneratorFunction接口，需要实现， 重与 map方法, 输入类型固定是Long
         *      第二个:Long类型，自动生成的数字序列 (从0自增)到最大值(小于)，达到这个值就停止了
         *      第三个:限速策略，比如 每秒生成几条数据
         *      第四个:返回的类型
         */
        DataGeneratorSource<String> dataGeneratorSource = new DataGeneratorSource<>(
                new GeneratorFunction<Long, String>() {
                    @Override
                    public String map(Long value) throws Exception {
                        return "Number: " + value;
                    }
                },
                10,
                RateLimiterStrategy.perSecond(1),
                Types.STRING
        );


        env.fromSource(dataGeneratorSource, WatermarkStrategy.noWatermarks(),"data-generator")
                        .print();

        env.execute();
    }
}
