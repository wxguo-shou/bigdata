package com.atguigu.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

/**
 * @author name 婉然从物
 * @create 2023-11-12 20:26
 */
object Spark01_Basic {
  def main(args: Array[String]): Unit = {
    // TODO 创建SparkSql的运行环境
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSql")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    // TODO 执行逻辑操作

    // DataFrame
//    val df: DataFrame = spark.read.json("atguigu-classes/datas/user.json")
//    df.show()

    // DataFrame => SQL
//    df.createOrReplaceTempView("user")

//    spark.sql("select * from user").show
//    spark.sql("select username from user").show
//    spark.sql("select avg(age) from user").show

    // DataFrame => DSL
    // 在使用DataFrame时， 如果涉及到转换操作， 需要引入转换规则

//    df.select("age", "username").show
//    df.select($"age" + 1).show
//    df.select('age + 1).show

    // TODO DataSet
    // DataFrame其实是特定泛型的DataSet
//    val seq = Seq(1, 2, 3, 4)
//    val ds: Dataset[Int] = seq.toDS()
//    ds.show()


    // RDD <=> DataFrame
    val rdd: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(List((1, "zhangsan", 30), (1, "lisi", 40)))
    val df: DataFrame = rdd.toDF("id", "name", "age")

    val rowRDD: RDD[Row] = df.rdd
    

    // DataFrame <=> DataSet
    val ds: Dataset[User] = df.as[User]
    val df1: DataFrame = ds.toDF()

    // RDD <=> DataSet
    val ds1: Dataset[User] = rdd.map {
      case (id, name, age) => {
        User(id, name, age)
      }
    }.toDS()

    val userRDD: RDD[User] = ds1.rdd


    // TODO 关闭环境
    spark.close()
  }

  case class User(id: Int, name: String, age: Int)

}
