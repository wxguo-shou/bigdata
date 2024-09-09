package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 20:08
 */
object Spark18_aggregateByKey2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - aggregateByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    // aggregateByKey最终的返回数据结果应该和初始值的类型保持一致

    // 获取相同key的数据的平均值
    val newRDD: RDD[(String, (Int, Int))] = rdd.aggregateByKey((0, 0))(
      (t, v) => (t._1 + v, t._2 + 1),
      (t1, t2) => (t1._1 + t2._1, t1._2 + t2._2)
    )

    val resultRDD = newRDD.mapValues {
      case (sum, cnt) =>
        sum / cnt
    }

    resultRDD.collect().foreach(println)

    sc.stop()

  }
}
