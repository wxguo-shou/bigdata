package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-02 16:15
 */
object Spark02_mapPartitions2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // TODO 算子 - mapPartitions
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)

    val mapRDD = rdd.mapPartitions(
      iter => {
        List(iter.max).iterator
      }
    )

    mapRDD.collect().foreach(println)

    sc.stop()
  }
}
