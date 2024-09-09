package com.atguigu.spark.core.acc

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-11 9:37
 */
object Spark01_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    val rdd: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4))

    val sumAcc: LongAccumulator = sc.longAccumulator("sum")

    rdd.foreach(sumAcc.add(_))

    println(sumAcc.value)


    sc.stop()
  }
}
