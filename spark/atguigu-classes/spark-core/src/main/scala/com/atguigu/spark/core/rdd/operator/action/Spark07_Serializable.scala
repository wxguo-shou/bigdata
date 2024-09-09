package com.atguigu.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}


/**
 * @author name 婉然从物
 * @create 2023-11-10 10:13
 */
object Spark07_Serializable {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1,2,3,4))

    val user = new User

    // Caused by: java.io.NotSerializableException: core.rdd.operator.action.Spark07_Serilisable$User

    // RDD算子中传递的函数是会包含闭包操作，那么就会进行检测功能
    // 闭包检测
    rdd.foreach(num => {
      println(user.age + num)
    })

    sc.stop()
  }

//  class User extends Serializable {
  // 样例类在编译时，会自动混入序列化特质(实现可序列化接口)
case class User() {
    var age: Int = 30
  }
}
