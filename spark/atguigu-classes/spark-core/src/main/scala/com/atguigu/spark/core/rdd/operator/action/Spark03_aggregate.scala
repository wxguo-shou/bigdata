package com.atguigu.spark.core.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-09 10:46
 */
object Spark03_aggregate {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    // aggregateByKey : 初始值只会参与分区内计算
    // aggregate : 初始值会参与分区内计算，并且和参与分区间计算

    // 10 + 13 + 17 = 40
    val result = rdd.aggregate(10)(_ + _, _ + _)

    val result1: Int = rdd.fold(10)(_ + _)

    println(s"result: $result, result1: $result1")
    
    
    sc.stop()
  }
}
