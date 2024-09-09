package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark13_TwoValue {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - 双Value类型

    // 交集，并集和差集要求两个数据源数据类型保持一致
    // 拉链操作两个数据源的类型可以不一致

    /*
    Can't zip RDDs with unequal numbers of partitions: List(2, 4)
    两个数据源要求分区数量要保持一致
    Can only zip RDDs with same number of elements in each partition
    两个数据源要求分区中数据数量保持一致
     */

    val rdd1 = sc.makeRDD(List(1, 2, 3, 4))
    val rdd2 = sc.makeRDD(List(3, 4, 5, 6))

    // 交集
    val itersection = rdd1.intersection(rdd2)
    println(itersection.collect().mkString(", "))

    // 并集
    val union = rdd1.union(rdd2)
    println(union.collect().mkString(", "))

    // 差集
    val subtract = rdd1.subtract(rdd2)
    println(subtract.collect().mkString(", "))

    // 拉链
    val zip = rdd1.zip(rdd2)
    println(zip.collect().mkString(", "))

    sc.stop()
  }
}
