package com.atguigu.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-02 9:54
 */
object Spark_02_RDD_File {
  def main(args: Array[String]): Unit = {
    // TODO 准备环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建RDD
    // 从文件中创建RDD， 将文件的数据作为处理的数据源
    // path路径默认以当前环境的根路径为基准。 可以写绝对路径， 也可以写相对路径
//    sc.textFile("F:\\bigdatacode\\spark\\atguigu-classes\\datas\\1.txt")
//    val rdd = sc.textFile("datas/1.txt")

    // path 路径可以是文件的具体路径， 也可以是目录名称
//    val rdd = sc.textFile("datas")
    // path 路径还可以 使用通配符 *
    val rdd = sc.textFile("datas/1*.txt")

    // path 路径还可以是 分布式  存储系统的系统路径 ：HDFS
//    val rdd = sc.textFile("hdfs://hadoop102/test.txt")

    rdd.collect().foreach(println)

    // TODO 关闭环境
    sc.stop()
  }
}
