package com.atguigu.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-12-27 9:28
 */
object pyspark01 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("test")

    val sc = new SparkContext(sparkConf)

    val rdd1: RDD[String] = sc.textFile("hdfs://hadoop102:8020/test/data/test.txt")

    val rdd2: RDD[(Char, Char, Char)] = rdd1.map(_.split("\t").mkString(","))
      .map(x => (x(0), x(1), x(2)))
      .filter(_._1 != "id")

    rdd2.collect.foreach(println)

    sc.stop()
  }
}
