package com.atguigu.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-09 9:33
 */
object Spark01_Action {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // TODO - 行动算子
    // 所谓的行动算子，其实就是触发作业(Job)执行的方法
    // 底层代码调用的是环境对象的runJob方法
    // 底层代码中会创建ActiveJob，并提交执行

    rdd.collect().foreach(println)


    sc.stop()
  }
}
