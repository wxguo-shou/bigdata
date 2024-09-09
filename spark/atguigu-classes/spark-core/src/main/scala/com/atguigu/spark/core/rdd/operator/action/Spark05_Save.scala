package com.atguigu.spark.core.rdd.operator.action

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-10 10:13
 */
object Spark05_Save {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    val rdd: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3)
    ), 8)

    // TODO - 行动算子
    rdd.saveAsTextFile("output")
    rdd.saveAsObjectFile("output1")
    rdd.saveAsSequenceFile("output2")

    sc.stop()
  }
}
