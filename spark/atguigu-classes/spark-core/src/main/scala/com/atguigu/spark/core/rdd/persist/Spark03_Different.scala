package com.atguigu.spark.core.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-10 19:48
 */
object Spark03_Different {
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

    mapRDD.cache()
    mapRDD.checkpoint()

    mapRDD.persist(StorageLevel.DISK_ONLY)

    val reduceRDD: RDD[(String, Int)] = mapRDD.reduceByKey(_ + _)

    reduceRDD.collect().foreach(println)

    val groupRDD: RDD[(String, Iterable[Int])] = mapRDD.groupByKey()

    groupRDD.collect().foreach(println)

    sc.stop()
  }
}

/*
  cache : 将数据临时存储在内存中进行数据重用
  persist: 将数据临时存储在磁盘文件中进行数据重用
           涉及到磁盘IO，性能较低，但是数据安全
           如果作业执行完毕，临时保存的数据文件就会丢失
  checkpoint: 将数据长久地保存在磁盘文件中进行数据重用
              涉及到磁盘IO，性能较低，但是数据安全
              为了保证数据安全，所以一般情况下，会独立执行作业
              为了能够提高效率，一般情况下是需要和cache联合使用
 */