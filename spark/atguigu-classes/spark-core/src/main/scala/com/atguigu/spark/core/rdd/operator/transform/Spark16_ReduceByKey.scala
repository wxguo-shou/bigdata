package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark16_ReduceByKey {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - reduceByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("b", 4)
    ))

    /*
    reduceByKey:相同的kev的数据进行value数据的聚合操作
    scala语言中一般的聚合操作都是两两聚合，spark基于scala开发的，所以它的聚合也是两两聚合操作
    【1，2，3】
    【3，3】
    【6】
    reduceByKey中如果key的数据只有一个，是不会参与运算的。
     */

    rdd.reduceByKey(_ + _).collect().foreach(println)

    sc.stop()
  }
}

/*
reduceByKey和groupByKey区别
  从 shuffle 的角度：reduceByKey 和 groupByKey 都存在 shuffle 的操作，但是 reduceByKey
  可以在 shuffle 前对分区内相同 key 的数据进行预聚合（combine）功能，这样会减少落盘的
  数据量，而 groupByKey 只是进行分组，不存在数据量减少的问题，reduceByKey 性能比较高。

  从功能的角度：reduceByKey 其实包含分组和聚合的功能。GroupByKey 只能分组，不能聚
  合，所以在分组聚合的场合下，推荐使用 reduceByKey，如果仅仅是分组而不需要聚合。那
  么还是只能使用 groupByKey
 */