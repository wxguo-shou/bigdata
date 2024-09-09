package com.atguigu.spark.test

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import java.util.Properties

/**
 * @author name 婉然从物
 * @create 2023-11-06 21:04
 */
object SparkKafkaProducer {
  def main(args: Array[String]): Unit = {
    // 0. 配置信息
    val properties = new Properties()
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092")
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])

    // 1. 创建一个生产者
    val producer = new KafkaProducer[String, String](properties)

    // 2. 发送数据
    for (elem <- 1 to 5) {
      producer.send(new ProducerRecord[String, String]("first","atguigu"+elem))
    }

    // 3. 关闭资源
    producer.close()
  }
}
