package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-02 16:15
 */
object Spark04_flatMap {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // TODO 算子 - flatMat
    val rdd = sc.makeRDD(List(List(1, 2), List(3, 4)), 2)

    val flatRDD = rdd.flatMap(list => list)

    flatRDD.collect().foreach(println)

    sc.stop()
  }
}
