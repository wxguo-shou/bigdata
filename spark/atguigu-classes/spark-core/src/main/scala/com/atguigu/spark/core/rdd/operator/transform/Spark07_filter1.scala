package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 15:09
 */
object Spark07_filter1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - filter
    val rdd = sc.textFile("atguigu-classes/datas/apache.log")

    rdd.filter(
      line => {
        val datas = line.split(" ")
        val time = datas(3)
        time.startsWith("17/05/2015")
      }
    ).collect().foreach(println)

    sc.stop()
  }
}
