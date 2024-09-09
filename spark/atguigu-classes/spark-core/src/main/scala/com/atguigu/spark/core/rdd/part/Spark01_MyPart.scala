package com.atguigu.spark.core.rdd.part

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-10-30 11:07
 */
object Spark01_MyPart {
  def main(args: Array[String]): Unit = {

    val SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(SparkConf)

    val rdd: RDD[(String, String)] = sc.makeRDD(List(
      ("nba", "......"),
      ("cba", "......"),
      ("wnba", "......"),
      ("nba", "......")
    ), 3)

    val partRDD: RDD[(String, String)] = rdd.partitionBy(new MyPartitioner)

    partRDD.saveAsTextFile("output")


    sc.stop()
  }
  class MyPartitioner extends Partitioner{
    // 分区数量
    override def numPartitions: Int = 3

    // 根据key 返回数据所在的分区索引
    override def getPartition(key: Any): Int = {
      key match {
        case "nba" => 0
        case "cba" => 1
        case _ => 2
      }
    }
  }
}
