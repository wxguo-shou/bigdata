package com.atguigu.spark.core.acc

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * @author name 婉然从物
 * @create 2023-11-11 11:34
 */
object Spark04_BC {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    val rdd1: RDD[(String, Int)] = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val map: mutable.Map[String, Int] = mutable.Map(("a", 4), ("b", 5), ("c", 6))

    val bc: Broadcast[mutable.Map[String, Int]] = sc.broadcast(map)

    rdd1.map{
      case (w, c) => {
        val l: Int = bc.value.getOrElse(w, 0)
        (w, (c, l))
      }
    }.collect().foreach(println)


    sc.stop()
  }
}

/*
  闭包数据，都是以Task为单位发送的，每个任务中包含闭包数据
  这样可能会导致，一个Executor中含有大量重复的数据，并且占用大量的内存
  Executor其实就一个JVM，所以在后动时，会自动分配内存
  完全可以将任务中的闭包数据放置在Executor的内存中，达到共享的目的
  Spark中的广播变量就可以将闭包的数据保存到Executor的内存中
  Spark中的广播变量不能够更改 : 分布式共享只读变量
 */