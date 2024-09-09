package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 20:08
 */
object Spark22_leftJoin_rightJoin {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - aggregateByKey
    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val rdd2 = sc.makeRDD(List(
      ("a", 2), ("a", 3)//, ("c", 5)
    ))

    val leftJoin: RDD[(String, (Int, Option[Int]))] = rdd1.leftOuterJoin(rdd2)

    leftJoin.collect().foreach(println)

    sc.stop()

  }
}
