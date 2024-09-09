package com.atguigu.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
 * @author name 婉然从物
 * @create 2023-11-12 20:26
 */
object Spark03_UDAF {
  def main(args: Array[String]): Unit = {
    // TODO 创建SparkSql的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    // TODO 执行逻辑操作
    val df: DataFrame = spark.read.json("atguigu-classes/datas/user.json")
    df.createOrReplaceTempView("user")

    spark.udf.register ("ageAvg", new MyAvgUDAF())

    spark.sql("select ageAvg(age) from user").show()


    // TODO 关闭环境
    spark.close()
  }

  /*
  自定义聚合函数类： 计算年龄的平均值
  1. 继承UserDefinedAggregateFunction
  2. 重写方法（8）
   */

  class MyAvgUDAF extends UserDefinedAggregateFunction{
    // 输入数据的结构: Input
    override def inputSchema: StructType = {
      StructType(
        Array(StructField("age", LongType))
      )
    }

    // 缓冲区数据的结构: Buffer
    override def bufferSchema: StructType = {
      StructType(
        Array(StructField("age", LongType), StructField("total", LongType))
      )
    }

    // 输出数据结构的类型: Out
    override def dataType: DataType = LongType

    // 函数的稳定性
    override def deterministic: Boolean = true

    // 缓冲区初始化
    override def initialize(buffer: MutableAggregationBuffer): Unit = {
      buffer(0) = 0L
      buffer(1) = 0L
    }

    // 根据输入的值更新缓冲区数据
    override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
      buffer.update(0, buffer.getLong(0) + input.getLong(0))
      buffer.update(1, buffer.getLong(1) + 1)
    }

    // 缓冲区数据合并
    override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
      buffer1.update(0, buffer1.getLong(0) + buffer2.getLong(0))
      buffer1.update(1, buffer1.getLong(1) + buffer2.getLong(1))
    }

    // 计算平均值
    override def evaluate(buffer: Row): Any = {
      buffer.getLong(0) / buffer.getLong(1)
    }
  }

}
