package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark12_sortBy {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - sortBy
    val rdd = sc.makeRDD(List(6, 3, 5, 2, 4, 6), 2)

    val sortRDD = rdd.sortBy(r => r)

    sortRDD.saveAsTextFile("output")

    sc.stop()
  }
}
