package com.atguigu.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql._

/**
 * @author name 婉然从物
 * @create 2023-11-12 20:26
 */
object Spark04_Hive {
  def main(args: Array[String]): Unit = {

    System.setProperty("HADOOP_USER_NAME", "root")
    // TODO 创建SparkSql的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()


    // 使用SparkSQL连接外置的Hive
    // 1. 拷贝Hive-site.xml文件到classpath下
    // 2. 启动Hive支持
    // 3. 增加对应的依赖关系（包含MySQL驱动）
    spark.sql("show tables").show

    spark.close()
  }
}
