package com.atguigu.spark.core.acc

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-11 9:37
 */
object Spark02_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    val sumAcc: LongAccumulator = sc.longAccumulator("sum")

    val mapRDD: RDD[Unit] = rdd.map(sumAcc.add(_))


    // 获取累加器的值
    // 少加: 转换算子中调用累加器，如果没有行动算子的话，那么不会执行
    // 多加:转换算子中调用累加器，如果没有行动算子的话，那么不会执行
    // 一般情况下，累加器会放置在行动算子进行操作
    mapRDD.collect()

    println(sumAcc.value)


    sc.stop()
  }
}
