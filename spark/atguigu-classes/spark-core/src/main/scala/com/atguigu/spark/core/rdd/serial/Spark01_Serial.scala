package com.atguigu.spark.core.rdd.serial

import com.atguigu.spark.core.rdd.serial.Spark01_Serial.Search
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-10 16:00
 */
object Spark01_Serial {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Serial")

    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.makeRDD(Array("hello world", "hello spark", "hive", "atguigu"))

    val search = new Search("h")

    // Caused by: java.io.NotSerializableException: core.rdd.serial.Spark01_Serial$Search
    search.getMatch1(rdd).collect().foreach(println)

    search.getMatch2(rdd).collect().foreach(println)

    sc.stop()
  }

  case class Search(query: String) {
    def isMatch(s: String): Boolean = {
      s.contains(query)
    }

    // 函数序列化案例
    def getMatch1(rdd: RDD[String]): RDD[String] = {
      //rdd.filter(this.isMatch)
      rdd.filter(isMatch)
    }


    // 属性序列化案例
    def getMatch2(rdd: RDD[String]): RDD[String] = {
      //rdd.filter(x => x.contains(this.query))
      rdd.filter(x => x.contains(query))
      //val q = query
      //rdd.filter(x => x.contains(q))
    }
  }
}
