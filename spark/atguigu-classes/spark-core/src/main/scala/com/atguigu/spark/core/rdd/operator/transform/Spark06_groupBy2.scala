package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * @author name 婉然从物
 * @create 2023-11-02 16:15
 */
object Spark06_groupBy2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // TODO 算子 - groupBy
    val rdd = sc.textFile("atguigu-classes/datas/apache.log")

    val timeRDD: RDD[(Int, Iterable[(Int, Int)])] = rdd.map(
      line => {
        val datas = line.split(" ")
        val time = datas(3)
        val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val date = sdf.parse(time)
//        date.getHours
        val calendar = Calendar.getInstance()
        calendar.setTime(date)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        (hour, 1)
      }
    ).groupBy(_._1)

//    timeRDD.collect().foreach(println)

//    timeRDD.map((hour, iter) => (hour, iter.size))

    timeRDD.map{
      case (hour, iter) => (hour, iter.size)
    }.collect().foreach(println)


    sc.stop()
  }
}