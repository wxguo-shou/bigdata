package com.atguigu.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-02 9:54
 */
object Spark_02_RDD_FileWho {
  def main(args: Array[String]): Unit = {
    // TODO 准备环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建RDD
    // textFile: 以行为单位读取数据， 读取的数据都是字符串
    // wholeTextFiles: 以文件为单位读取数据
    // 读取的结果表示为元组， 第一个元素表示文件路径，第二个元素表示文件内容
    val rdd = sc.wholeTextFiles("datas")

    rdd.collect().foreach(println)

    // TODO 关闭环境
    sc.stop()
  }
}
