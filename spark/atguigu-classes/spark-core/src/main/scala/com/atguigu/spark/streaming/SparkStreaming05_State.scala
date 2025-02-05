package com.atguigu.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author name 婉然从物
 * @create 2023-11-15 19:37
 */
object SparkStreaming05_State {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")

    val ssc = new StreamingContext(sparkConf, Seconds(3))
    // 有状态时， 需要设置检查点路径
    ssc.checkpoint("hdfs://hadoop102:8020/cp")


    // 无状态数据操作， 只对当前的采集周期内的数据进行处理
    // 在某些场合下， 需要保留数据统计结果（状态）， 实现数据的汇总
    val datas = ssc.socketTextStream("hadoop102", 9999)

    val wordToOne = datas.map((_, 1))

    // updateStateByKey： 根据key对数据的状态进行更新
    // 传递的参数中含有两个值
    // 第一个值表示相同的key的value数据
    // 第二个值表示缓冲区相同key的value数据
//    val wordToCount = wordToOne.reduceByKey(_ + _)
    val state = wordToOne.updateStateByKey(
      (seq: Seq[Int], buff: Option[Int]) => {
        val newCount = buff.getOrElse(0) + seq.sum
        Option(newCount)
      }
    )

    state.print()

    ssc.start()

    ssc.awaitTermination()
  }

}
