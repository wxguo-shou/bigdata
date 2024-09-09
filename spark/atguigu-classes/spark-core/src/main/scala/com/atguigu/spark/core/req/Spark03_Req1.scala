package com.atguigu.spark.core.req

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import java.io
import javax.validation.constraints.Past

/**
 * @author name 婉然从物
 * @create 2023-11-12 9:16
 */
object Spark03_Req1 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Req")

    val sc = new SparkContext(sparkConf)

    // Q1: actionRDD 重复使用
    // Q2: cogroup可能性能较低

    // 1. 读取原始日志数据
    val actionRDD: RDD[String] = sc.textFile("atguigu-classes/datas/user_visit_action.txt")

    // 2. 将数据转换结构
    // 点击的场合: (品类ID, (1, 0, 0))
    // 下单的场合: (品类ID, (0, 1, 0))
    // 支付的场合: (品类ID, (0, 0, 1))
    val flatRDD: RDD[(String, (Int, Int, Int))] = actionRDD.flatMap(
      action => {
        val datas: Array[String] = action.split("_")
        datas match {
          case _ if datas(6) != "-1" => List((datas(6), (1, 0, 0)))
          case _ if datas(8) != "null" => {
            val ids: Array[String] = datas(8).split(",")
            ids.map(id => (id, (0, 1, 0)))
          }
          case _ if datas(10) != "null" => {
            val ids: Array[String] = datas(10).split(",")
            ids.map(id => (id, (0, 0, 1)))
          }
          case _ => Nil
        }

      }
    )


    // 3. 统计品类的点击数量 （品类ID， 点击数量）
    val analysisRDD: RDD[(String, (Int, Int, Int))] = flatRDD.reduceByKey(
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3)
      }
    )


    val resultRDD: Array[(String, (Int, Int, Int))] = analysisRDD.sortBy(_._2, false).take(10)

    resultRDD.foreach(println)

    sc.stop()

  }
}
