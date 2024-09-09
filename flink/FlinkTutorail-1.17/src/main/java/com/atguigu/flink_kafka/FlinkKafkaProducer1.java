package com.atguigu.flink_kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author name 婉然从物
 * @create 2023-11-06 19:20
 */
public class FlinkKafkaProducer1 {
    public static void main(String[] args) throws Exception {
        // 1. 获取环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);

        // 2. 准备数据源
        ArrayList<String> wordlist = new ArrayList<>();
        wordlist.add("hello");
        wordlist.add("kafka");
        DataStreamSource<String> stream = env.fromCollection(wordlist);

        // 创建一个kafka 生产者
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092");

        FlinkKafkaProducer<String> kafkaProducer = new FlinkKafkaProducer<>("first", new SimpleStringSchema(), properties);

        // 3. 添加数据源 Kafka生产者
        stream.addSink(kafkaProducer);

        // 4. 执行代码
        env.execute();
    }
}
