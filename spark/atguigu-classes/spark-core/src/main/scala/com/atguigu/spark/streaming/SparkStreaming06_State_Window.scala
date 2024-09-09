package com.atguigu.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author name 婉然从物
 * @create 2023-11-15 19:37
 */
object SparkStreaming06_State_Window {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")

    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val lines = ssc.socketTextStream("localhost", 9999)

    val wordToOne: DStream[(String, Int)] = lines.map((_, 1))

    // 窗口的范围应该是采集周期的整数倍
    val windowDS: DStream[(String, Int)] = wordToOne.window(Seconds(6), Seconds(6))

    val wordToCount: DStream[(String, Int)] = windowDS.reduceByKey(_ + _)

    wordToCount.print()

    ssc.start()

    ssc.awaitTermination()
  }

}
