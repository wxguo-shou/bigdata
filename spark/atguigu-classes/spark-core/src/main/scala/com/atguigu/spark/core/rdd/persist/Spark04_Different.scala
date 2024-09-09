package com.atguigu.spark.core.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-10 19:48
 */
object Spark04_Different {
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

    // cache, persist 会在血缘关系中添加新的依赖。一旦，出现问题，可以从头读取数据
//    mapRDD.cache()

    // 执行过程中，会切断血缘关系。重新建立新的血缘关系 等同于改变数据源
    mapRDD.checkpoint()

    println(mapRDD.toDebugString)

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)


    reduceRDD.collect().foreach(println)
    println("====================")
    println(mapRDD.toDebugString)

    sc.stop()
  }
}
