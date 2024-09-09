package com.atguigu.spark.core.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-10 19:48
 */
object Spark02_CheckPoint {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Serial")

    val sc = new SparkContext(sparkConf)
    sc.setCheckpointDir("cp")

    val list = List("hello world", "hello spark", "hive", "atguigu")

    val rdd: RDD[String] = sc.makeRDD(list)

    val flatRDD: RDD[String] = rdd.flatMap(_.split(" "))

    val mapRDD: RDD[(String, Int)] = flatRDD.map(
      word => {
        println("*****************")
        (word, 1)
      }
    )

    // checkpoint 需要落盘，需要指定检查点保存路径
    // 检查点路径保存的文件，当作业执行完毕后，不会被删除
    // 一般保存路径都是在分布式存储系统: HDFS
    mapRDD.checkpoint()

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)

    reduceRDD.collect().foreach(println)

    val groupRDD: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()

    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
