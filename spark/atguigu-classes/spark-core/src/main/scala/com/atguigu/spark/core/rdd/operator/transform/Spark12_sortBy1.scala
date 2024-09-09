package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 8:55
 */
object Spark12_sortBy1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - sortBy
    val rdd = sc.makeRDD(List(("1",1), ("11",2), ("2",3)), 2)

    // sortBy方法可以根据指定的规则对数据源中的数据进行排序， 默认为升序（第二个参数可以改变排序方式）
    // sortBy默认情况下， 不会改变分区， 但是中间存在shuffle操作
    val sortRDD = rdd.sortBy(_._1.toInt, false)

    sortRDD.collect().foreach(println)

    sc.stop()
  }
}
