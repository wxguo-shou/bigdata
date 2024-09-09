package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark07_filter {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - filter
    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    val filterRDD = rdd.filter(_ % 2 != 0)

    filterRDD.collect().foreach(println)

    sc.stop()
  }
}
