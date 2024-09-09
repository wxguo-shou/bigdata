package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark14_GroupByKey {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - groupByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("b", 4)
    ))

    val groupByKeyRDD: RDD[(String, Iterable[Int])] = rdd.groupByKey()
      groupByKeyRDD.collect().foreach(println)

    val groupByRDD = rdd.groupBy(_._1)

    sc.stop()
  }
}

