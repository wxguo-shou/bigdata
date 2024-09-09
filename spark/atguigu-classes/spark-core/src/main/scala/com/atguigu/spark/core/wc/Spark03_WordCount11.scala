package com.atguigu.spark.core.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-09 11:21
 */
object Spark03_WordCount11 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("loacl[*]").setAppName("WordCount")

    val sc = new SparkContext(sparkConf)





    sc.stop()
  }

  // groupBy
  def wordcount1(sc: SparkContext) ={
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val group: RDD[(String, Iterable[String])] = words.groupBy(word => word)
    val wordCount: RDD[(String, Int)] = group.mapValues(_.size)
  }

  // groupByKey
  def wordcount2(sc: SparkContext) = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val group: RDD[(String, Iterable[Int])] = wordOne.groupByKey()
    val wordCount: RDD[(String, Int)] = group.mapValues(_.size)
  }

  // reduceByKey
  def wordcount3(sc: SparkContext) = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val reduce: RDD[(String, Int)] = wordOne.reduceByKey(_ + _)
  }

  // aggregateByKey
  def wordcount4(sc: SparkContext) = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val aggregate: RDD[(String, Int)] = wordOne.aggregateByKey(0)(_+_, _+_)
  }

  // foldByKey
  def wordcount5(sc: SparkContext) = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val fold: RDD[(String, Int)] = wordOne.foldByKey(0)(_ + _)
  }

  // combineByKey
  def wordcount6(sc: SparkContext) = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val combine: RDD[(String, Int)] = wordOne.combineByKey(
      x => x,
      (x: Int, y) => x + y,
      (x: Int, y: Int) => x + y
    )
  }

  // countByKey
  def wordcount7(sc: SparkContext) = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne: RDD[(String, Int)] = words.map((_, 1))
    val count: collection.Map[String, Long] = wordOne.countByKey()
  }

  // countByValue
  def wordcount8(sc: SparkContext) = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val stringToLong: collection.Map[String, Long] = words.countByValue()
  }



}
