package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 20:08
 */
object Spark17_aggregateFoldByKey {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 -
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ), 2)

//    rdd.aggregateByKey(5)(_ + _, _ + _).collect().foreach(println)

    rdd.foldByKey(0)(_+_).collect().foreach(println)

    sc.stop()

  }
}
