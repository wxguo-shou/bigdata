package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark15_KeyValue {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - Key-Value类型
    val rdd = sc.makeRDD(List(1,2,3,4), 2)

    val mapRDD = rdd.map((_, 1))

    mapRDD.partitionBy(new HashPartitioner(2))
      .saveAsTextFile("output")

    sc.stop()
  }
}
