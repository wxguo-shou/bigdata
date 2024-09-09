package com.atguigu.spark.core.rdd.dep

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-10-30 11:07
 */
object Spark01_Dep {
  def main(args: Array[String]): Unit = {

    val SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(SparkConf)

    val lines: RDD[String] = sc.textFile("atguigu-classes/datas/word.txt")
    println(lines.toDebugString)
    println("---------------------------------")

    val words: RDD[String] = lines.flatMap(_.split(" "))
    println(words.toDebugString)
    println("---------------------------------")

    val wordToOne = words.map(word => (word, 1))
    println(wordToOne.toDebugString)
    println("---------------------------------")

    val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
    println(wordToCount.toDebugString)
    println("---------------------------------")

    val array: Array[(String, Int)] = wordToCount.collect()
    array.foreach(println)

    sc.stop()
  }
}
