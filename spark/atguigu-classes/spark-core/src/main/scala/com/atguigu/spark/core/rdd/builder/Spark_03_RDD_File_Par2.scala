package com.atguigu.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-02 9:54
 */
object Spark_03_RDD_File_Par2 {
  def main(args: Array[String]): Unit = {
    // TODO 准备环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建RDD
    // 14byte / 2 = 7byte
    // 14 / 2 = 7(分区)
    /*
        1234567@@ => 012345678
        89@@      => 9101112
        0         => 13

        [0, 7] => 1234567
        [7, 14] => 890
     */
     // 如果数据源为多个文件时， 那么计算分区时以文件为单位进行分区
    val rdd = sc.textFile("datas/word.txt", 2)

    rdd.saveAsTextFile("output")

    // TODO 关闭环境
    sc.stop()
  }
}
