package com.atguigu.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author name 婉然从物
 * @create 2023-11-15 19:05
 */
object SparkStreaming01_WordCount {
  def main(args: Array[String]): Unit = {
    // TODO 创建环境对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")

    // 第一个参数表示环境配置， 第二个参数表示批量处理的周期（采集周期 ）
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    // TODO 逻辑处理
    val lines: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102", 9999)

    val words: DStream[String] = lines.flatMap(_.split(" "))

    val wordToOne: DStream[(String, Int)] = words.map((_, 1))

    val wordToCount: DStream[(String, Int)] = wordToOne.reduceByKey(_ + _)

    wordToCount.print()

    // 由于SparkStreaming采集器是长期执行的任务，所以不能直接关闭
    // 如果main方法执行完毕，应用程序也会自动结束。所以不能让main执行完毕
    // ssc. stop()
    // 1. 启动来集器
    ssc.start()
    //2.等待采集器的关闭
    ssc.awaitTermination()

  }
}
