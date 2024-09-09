package com.atguigu.spark.core.req

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-12 9:16
 */
object Spark02_Req1 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Req")

    val sc = new SparkContext(sparkConf)

    // Q1: actionRDD 重复使用
    // Q2: cogroup可能性能较低

    // 1. 读取原始日志数据
    val actionRDD: RDD[String] = sc.textFile("atguigu-classes/datas/user_visit_action.txt")
    actionRDD.cache()   // 放缓存， 提高性能

    // 2. 统计品类的点击数量 （品类ID， 点击数量）
    val clickActionRDD: RDD[String] = actionRDD.filter(
      action => {
        val datas: Array[String] = action.split("_")
        datas(6) != "-1"
      }
    )

    val clickCountRDD: RDD[(String, Int)] = clickActionRDD.map(
      action => {
        val datas: Array[String] = action.split("_")
        (datas(6), 1)
      }
    ).reduceByKey(_ + _)

    // 3. 统计品类的下单数量: (品类ID, 下单数量 )
    val orderActionRDD: RDD[String] = actionRDD.filter(
      action => {
        val datas: Array[String] = action.split("_")
        datas(8) != "null"
      }
    )

    val orderCountRDD: RDD[(String, Int)] = orderActionRDD.flatMap(
      action => {
        val datas: Array[String] = action.split("_")
        val cid: String = datas(8)
        val cids: Array[String] = cid.split(",")
        cids.map((_, 1))
      }
    ).reduceByKey(_ + _)

    // 4. 统计品类的支付数量
    val payActionRDD: RDD[String] = actionRDD.filter(
      action => {
        val datas: Array[String] = action.split("_")
        datas(10) != "null"
      }
    )

    val payCountRDD: RDD[(String, Int)] = payActionRDD.flatMap(
      action => {
        val datas: Array[String] = action.split("_")
        val cid: String = datas(10)
        val cids: Array[String] = cid.split(",")
        cids.map((_, 1))
      }
    ).reduceByKey(_ + _)

    // ( 品类ID，点击数量 ) => ( 品类ID，( 点击数量， 0，0 ))
    // ( 品类ID，下单数量 ) => ( 品类ID，( 0， 下单数量，0 ))
    // ( 品类ID，支付数量 ) => ( 品类ID，( 0， 0，支付数量 ))
    // ( 品类ID，( 点击数量， 下单数量，支付数量 ))

    // 5.将品类进行排序，并且取前10名
    //    点击数量排序，下单数量排序，支付数量排序
    //    元组排序:先比较第一个，再比较第二个，再比较第三个，依此类推
    //    ( 品类ID，( 点击数量， 下单数量，支付数量 ))
    // cogroup = connect + group   有可能存在shuffle
    val rdd1: RDD[(String, (Int, Int, Int))] = clickCountRDD.mapValues((_, 0, 0))
    val rdd2: RDD[(String, (Int, Int, Int))] = orderCountRDD.mapValues((0, _, 0))
    val rdd3: RDD[(String, (Int, Int, Int))] = payCountRDD.mapValues((0, 0, _))

    val sourceRDD: RDD[(String, (Int, Int, Int))] = rdd1.union(rdd2).union(rdd3)

    val analysisRDD: RDD[(String, (Int, Int, Int))] = sourceRDD.reduceByKey(
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3)
      }
    )


    val resultRDD: Array[(String, (Int, Int, Int))] = analysisRDD.sortBy(_._2, false).take(10)

    resultRDD.foreach(println)




    sc.stop()

  }
}
