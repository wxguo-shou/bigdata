package com.atguigu.spark.core.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-10-30 11:07
 */
object Spark02_WordCount {
  def main(args: Array[String]): Unit = {
    // Application
    // Spark框架
    // TODO 建立和Spark框架的连接
    // JDBC: Connection
    val SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(SparkConf)

    // TODO 执行业务操作
    val lines: RDD[String] = sc.textFile("datas")

    val words: RDD[String] = lines.flatMap(_.split(" "))

    val wordToOne = words.map(word => (word, 1))

    val wordGroup: RDD[(String, Iterable[(String, Int)])] = wordToOne.groupBy(_._1)

    val wordToCount: RDD[(String, Int)] = wordGroup.map {
      case (word, list) => list.reduce(
          (t1, t2) => (t1._1, t1._2 + t2._2)
        )
    }

    // 5. 将结果采集到控制台打印
    val array: Array[(String, Int)] = wordToCount.collect()
    array.foreach(println)

    // TODO 关闭连接
    sc.stop()
  }
}
