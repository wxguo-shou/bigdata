package com.atguigu.spark.core.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-10 19:48
 */
object Spark01_Persist {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Serial")

    val sc = new SparkContext(sparkConf)

    val list = List("hello world", "hello spark", "hive", "atguigu")

    val rdd: RDD[String] = sc.makeRDD(list)

    val flatRDD: RDD[String] = rdd.flatMap(_.split(" "))

    val mapRDD: RDD[(String, Int)] = flatRDD.map(
      word => {
        println("*****************")
        (word, 1)
      }
    )

    mapRDD.cache()

    // 指定持久位置  用persist
    // 持久化操作 必须在行动算子执行时完成
    // RDD对象的持久化操作不一定是为了重用
    // 在数据执行较长，或数据比较重要的场合也可以采用持久化操作
    mapRDD.persist(StorageLevel.DISK_ONLY)

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)

    reduceRDD.collect().foreach(println)

    val groupRDD: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()

    groupRDD.collect().foreach(println)

    sc.stop()
  }
}
