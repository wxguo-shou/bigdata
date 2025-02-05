package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark10_coalesce {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - coalesce
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 3)

    /*
    coalesce方法默认情况下不会将分区的数据打乱重新组合
    这种情况下的缩减分区可能会导致数据不均衡，出现数据倾斜
    如果想要让数据均衡，可以进行shuffle处理
     */

    val newRDD = rdd.coalesce(2, true)

    newRDD.saveAsTextFile("output")

    sc.stop()
  }
}
