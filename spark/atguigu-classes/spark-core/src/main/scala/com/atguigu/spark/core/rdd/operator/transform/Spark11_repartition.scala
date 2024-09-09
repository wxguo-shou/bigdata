package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark11_repartition {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - repartition
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)

    /*
    coalesce算子可以扩大分区的，但是如果不进行shuffle操作，是没有意义，不起作用.
    所以如果想要实现扩大分区的效果，需要使用shuffle操作
    spark提供了一个简化的操作
    缩减分区: coalesce，如果想要数据均衡，可以来用shuffle
    扩大分区: repartition，底层代码调用的就是coalesce，而且肯定来用shuffle
     */

//    val newRDD = rdd.coalesce(3, true)

    val newRDD = rdd.repartition(3)

    newRDD.saveAsTextFile("output")

    sc.stop()
  }
}
