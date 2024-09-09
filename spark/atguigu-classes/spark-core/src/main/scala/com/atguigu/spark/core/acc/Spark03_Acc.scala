package com.atguigu.spark.core.acc

import org.apache.spark.rdd.RDD
import org.apache.spark.util.{AccumulatorV2, LongAccumulator}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.util.parsing.json.JSON.headOptionTailToFunList

/**
 * @author name 婉然从物
 * @create 2023-11-11 9:37
 */
object Spark03_Acc {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Acc")

    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List("hello", "spark", "hello"))

    // 累加器
    // 1. 创建累加器对象
    val wcAcc = new MyAccumulator
    // 2. 向Spark进行注册
    sc.register(wcAcc, "wordCountAcc")

    rdd.foreach(
      word => {
        // 数据的累加， 使用累加器
        wcAcc.add(word)
      }
    )

    // 获取累加器累加的结果
    println(wcAcc.value)


    sc.stop()
  }

  /*
  自定义数据累加器： WordCount

  1. 继承AccumulatorV2， 定义泛型
    IN: 累加器输入的数据类型  String
    OUT: 累加器返回的数据类型  mutable.Map[String, Long]

  2. 重写方法
   */
  class MyAccumulator extends AccumulatorV2[String, mutable.Map[String, Long]]{
    // 加括号和不加括号的区别在于是否实例化了该对象。加上括号后，wcMap是一个实际的对象，可以进行操作；
    // 而不加括号时，wcMap只是一个类型声明，不能进行任何操作
    private var wcMap = mutable.Map[String, Long]()

    // 判断是否为初始状态
    override def isZero: Boolean = {
      wcMap.isEmpty
    }

    override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = {
      new MyAccumulator
    }

    override def reset(): Unit = {
      wcMap.clear()
    }

    // 获取累加器需要计算的值
    override def add(word: String): Unit = {
      val newCnt: Long = wcMap.getOrElse(word, 0L) + 1
      wcMap.update(word, newCnt)
    }

    // Driver合并多个累加器
    override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
      val map1: mutable.Map[String, Long] = this.wcMap
      val map2: mutable.Map[String, Long] = other.value

      map2.foreach{
        case (word, count) => {
          val newCount: Long = map1.getOrElse(word, 0L) + count
          map1.update(word, newCount)
        }
      }
    }

    override def value: mutable.Map[String, Long] = {
      wcMap
    }
  }
}
