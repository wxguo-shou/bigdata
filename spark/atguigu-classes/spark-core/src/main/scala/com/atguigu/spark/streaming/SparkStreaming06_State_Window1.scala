package com.atguigu.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author name 婉然从物
 * @create 2023-11-15 19:37
 */
object SparkStreaming06_State_Window1 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")

    val ssc = new StreamingContext(sparkConf, Seconds(3))
    ssc.checkpoint("/cp")

    val lines = ssc.socketTextStream("hadoop102", 9999)

    val wordToOne: DStream[(String, Int)] = lines.map((_, 1))

    // reduceByKeyAndWindow: 当窗口范围比较大， 但是滑动幅度比较小， 就可以采用增加数据和删除数据的方式
    // 无需重复计算， 提升性能

    val windowDS: DStream[(String, Int)] = wordToOne.reduceByKeyAndWindow(
      _ + _,
      _ - _,
      Seconds(9),
      Seconds(3)
    )

    windowDS.print()

    ssc.start()

    ssc.awaitTermination()
  }

}
