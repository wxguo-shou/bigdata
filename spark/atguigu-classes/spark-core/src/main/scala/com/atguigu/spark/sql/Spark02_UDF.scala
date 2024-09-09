package com.atguigu.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * @author name 婉然从物
 * @create 2023-11-12 20:26
 */
object Spark02_UDF {
  def main(args: Array[String]): Unit = {
    // TODO 创建SparkSql的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    // TODO 执行逻辑操作
    val df: DataFrame = spark.read.json("atguigu-classes/datas/user.json")
    df.createOrReplaceTempView("user")

    spark.udf.register ("prefixName", (name: String) => {"Name: " + name})

    spark.sql("select age, prefixName(username) from user").show()


    // TODO 关闭环境
    spark.close()
  }

}
