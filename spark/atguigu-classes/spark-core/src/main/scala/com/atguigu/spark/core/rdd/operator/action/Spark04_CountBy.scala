package com.atguigu.spark.core.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-09 10:57
 */
object Spark04_CountBy {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 1, 5))

    val intToLong: collection.Map[Int, Long] = rdd.countByValue()

    println(intToLong)

    println("---------------------")


    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("a", 3), ("a", 2)
    ))

    val stringToLong: collection.Map[String, Long] = rdd1.countByKey()

    println(stringToLong)


    sc.stop()
  }
}
