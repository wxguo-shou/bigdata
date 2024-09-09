package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 20:08
 */
object Spark17_aggregateByKey {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 -
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    // aggregateBykey存在函数柯里化，有两个参数列表
    // 第一个参数列表, 需要传递一个参数， 表示为初始值
    //    主要用于当碰见第一个key的时候，和value进行分区内计算
    // 第二个参数列表需要传递2个参数
    //    第一个参数表示分区内计算规则
    //    第二个参数表示分区间计算规则
    rdd.aggregateByKey(5)(
      math.max(_, _),
      _ + _
    )
      .collect().foreach(println)

    sc.stop()
  }
}
