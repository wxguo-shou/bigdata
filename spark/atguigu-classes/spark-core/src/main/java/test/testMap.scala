package test

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2024-03-28 15:33
 */
object testMap {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("testMap")

    val sc = new SparkContext(sparkConf)

    val fileRDD: RDD[String] = sc.textFile("file://C:/Users/wxguo/Desktop/adult/adult/adult.data")

    val split_RDD: RDD[Array[String]] = fileRDD
      .map(r => r.split(","))

    println(split_RDD.collect())

    sc.stop()
  }
}
