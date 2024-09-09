package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-02 16:15
 */
object Spark01_map2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // TODO 算子 - map

    val rdd = sc.textFile("datas/apache.log")

    val mapRDD = rdd.map(
      line => {
        val datas = line.split(" ")
        datas(6)
      }
    )

    mapRDD.collect().foreach(println)

    sc.stop()
  }
}
