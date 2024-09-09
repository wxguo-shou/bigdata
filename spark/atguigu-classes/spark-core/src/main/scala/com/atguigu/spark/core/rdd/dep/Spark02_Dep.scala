package com.atguigu.spark.core.rdd.dep

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-10-30 11:07
 */
object Spark02_Dep {
  def main(args: Array[String]): Unit = {

    val SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(SparkConf)

    val lines: RDD[String] = sc.textFile("atguigu-classes/datas/word.txt")
    println(lines.dependencies)
    println("---------------------------------")

    val words: RDD[String] = lines.flatMap(_.split(" "))
    println(words.dependencies)
    println("---------------------------------")

    val wordToOne = words.map(word => (word, 1))
    println(wordToOne.dependencies)
    println("---------------------------------")

    val wordToCount: RDD[(String, Int)] = wordToOne.reduceByKey(_ + _)
    println(wordToCount.dependencies)
    println("---------------------------------")

    val array: Array[(String, Int)] = wordToCount.collect()
    array.foreach(println)

    sc.stop()
  }
}
