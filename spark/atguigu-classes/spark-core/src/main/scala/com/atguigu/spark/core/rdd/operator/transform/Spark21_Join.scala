package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 20:08
 */
object Spark21_Join {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - aggregateByKey
    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val rdd2 = sc.makeRDD(List(
      ("a", 2), ("a", 3), ("c", 5)
    ))


    // 两个不同数据源的数据，相同的kev的value会连接在一起，形成元组
    // 如果两个数据源中kev没有匹配上，那么数据不会出现在结果中
    // 如果两个数据源中kev有多个相同的，会依次匹配，可能会出现笛卡尔乘积,数据量会几何增长，导致性能降低
    val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)

    joinRDD.collect().foreach(println)


    sc.stop()

  }
}
