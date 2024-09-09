package com.atguigu.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Aggregator

/**
 * @author name 婉然从物
 * @create 2023-11-12 20:26
 */
object Spark04_JDBC {
  def main(args: Array[String]): Unit = {
    // TODO 创建SparkSql的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._



    // TODO 关闭环境
    spark.close()
  }



}
