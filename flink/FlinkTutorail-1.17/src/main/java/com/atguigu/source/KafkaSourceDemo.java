package com.atguigu.source;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KafkaSourceDemo {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        // TODO 从Kafka读  新Source架构
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers("hadoop102:9092, hadoop103:9092, hadoop104:9092")  // 指定kafka节点的地址和端口
                .setGroupId("atguigu")  // 指定消费者组的id
                .setTopics("topic_1")   // 指定消费组的Topic
                .setValueOnlyDeserializer(new SimpleStringSchema())     // 指定反序列化器， 这个只反序列化value
                .setStartingOffsets(OffsetsInitializer.latest())    // flink消费kafka的策略
                .build();
        env
                .fromSource(kafkaSource, WatermarkStrategy.noWatermarks(), "kafkasource")
                .print();

        env.execute();
    }
}

/**
 * kafka消费者的参数:
 *      auto.reset.offsets
 *      earliest: 如果有offset，从offset继续消费，如果没有offset，从 最早消费
 *      Latest:   如果有offset，从offset继续消费，如果没有offset，从 最新消费.
 * fLink的kafkasource，offset消费策略:0ffsetsInitializer， 默认是 earliest
 *      earliest: 一定从 最早 消费
 *      latest:   一定从 最新 消费
 */
