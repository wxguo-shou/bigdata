package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-02 16:15
 */
object Spark06_groupBy1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // TODO 算子 - groupBy
    val rdd = sc.makeRDD(List("Hadoop", "Spark", "Scala", "Hello"), 2)

    val groupRDD = rdd.groupBy(_.charAt(0))

    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
