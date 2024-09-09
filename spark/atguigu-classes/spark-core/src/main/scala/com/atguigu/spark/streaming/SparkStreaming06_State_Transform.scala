package com.atguigu.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author name 婉然从物
 * @create 2023-11-15 19:37
 */
object SparkStreaming06_State_Transform {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")

    val ssc = new StreamingContext(sparkConf, Seconds(3))
    // 有状态时， 需要设置检查点路径
    ssc.checkpoint("hdfs://hadoop102:8020/cp")


    // 无状态数据操作， 只对当前的采集周期内的数据进行处理
    // 在某些场合下， 需要保留数据统计结果（状态）， 实现数据的汇总
    val lines = ssc.socketTextStream("localhost", 9999)

    // transform 可以将底层RDD获取到后进行操作
    // DStream功能不完善
    // 需要代码周期性执行
    // Code: Driver端
    val newDS: DStream[String] = lines.transform(
      rdd => {
        // Code: Driver端 周期性执行
        rdd.map{
          str => {
            // Code: Executor端
            str
          }
        }
      }
    )

    // Code: Driver端
    val newDS1: DStream[String] = lines.map{
      data => {
        // Code: Executor端
        data
      }
    }


    ssc.start()

    ssc.awaitTermination()
  }

}
