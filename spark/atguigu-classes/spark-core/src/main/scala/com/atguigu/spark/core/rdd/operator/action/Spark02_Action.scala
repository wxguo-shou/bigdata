package com.atguigu.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-09 9:33
 */
object Spark02_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 4, 2, 3))

    val i: Int = rdd.reduce(_ + _)

    println(i)
    println("=========================")

    val ints: Array[Int] = rdd.collect()
    println(ints.mkString(","))

    println("=========================")

    val cnt: Long = rdd.count()
    println(cnt)

    println("=========================")

    val first: Int = rdd.first()
    println(first)
    
    println("=========================")

    val ints1: Array[Int] = rdd.take(3)
    println(ints1.mkString(", "))
    
    println("=========================")

    val ints2: Array[Int] = rdd.takeOrdered(3)(Ordering[Int].reverse)
    println(ints2.mkString(", "))

    println("=========================")


    sc.stop()
  }
}
