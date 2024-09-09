package com.atguigu.spark.core.wc

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-10-30 11:07
 */
object Spark01_WordCount {
  def main(args: Array[String]): Unit = {
    // Application
    // Spark框架
    // TODO 建立和Spark框架的连接
    // JDBC: Connection
    val SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(SparkConf)

    // TODO 执行业务操作
    // 1. 读取文件， 获取一行一行的数据
    val lines: RDD[String] = sc.textFile("datas")

    // 2. 将一行数据拆分， 形成一个一个的单词（分词）
    // 扁平化
    val words: RDD[String] = lines.flatMap(_.split(" "))

    // 3. 将数据根据单词进行分组， 便于统计
    val wordGroup: RDD[(String, Iterable[String])] = words.groupBy(word => word)

    // 4. 对分组后的数据进行转换
    val wordToCount = wordGroup.map {
      case (word, list) => (word, list.size)
    }

    // 5. 将结果采集到控制台打印
    val array: Array[(String, Int)] = wordToCount.collect()
    array.foreach(println)

    // TODO 关闭连接
    sc.stop()
  }
}
