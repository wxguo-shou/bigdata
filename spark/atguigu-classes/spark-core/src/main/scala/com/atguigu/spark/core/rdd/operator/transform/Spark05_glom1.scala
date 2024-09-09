package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-02 16:15
 */
object Spark05_glom1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // TODO 算子 - glom
    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4), 2)

    val glomRDD: RDD[Array[Int]] = rdd.glom()

    val maxRDD: RDD[Int] = glomRDD.map(_.max)

    println(maxRDD.collect().sum)

    sc.stop()
  }
}
