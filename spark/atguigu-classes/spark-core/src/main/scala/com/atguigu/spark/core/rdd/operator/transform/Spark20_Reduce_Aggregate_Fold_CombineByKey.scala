package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-07 20:08
 */
object Spark20_Reduce_Aggregate_Fold_CombineByKey {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // TODO 算子 - aggregateByKey
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6)
    ), 2)


    /*
    reduceByKey:
            combineByKeyWithClassTag[V](
            (v: V) => v,    第一个值不会参与计算
            func,           分区内计算规则
            func,           分区间计算规则
            partitioner)

    aggregateByKey:
        combineByKeyWithClassTag[U]((v: V) =>
        cleanedSeqOp(createZero(), v),    初始值和第一个key的value值进行的分区内数据操作
          cleanedSeqOp,     分区内计算规则
          combOp,           分区间计算规则
          partitioner)

    foldByKey:
        combineByKeyWithClassTag[V](
        (v: V) => cleanedFunc(createZero(), v), 初始值和第一个key的value值进行的分区内数据操作
        cleanedFunc,       表示分区内计算规则
        cleanedFunc,       分区间计算规则
        partitioner)

    combineByKey:
        combineByKeyWithClassTag(
        createCombiner,     相同kev的第一条数据进行的处理函数表示
        mergeValue,         表示分区内数据的处理函数
        mergeCombiners,     分区间数据的处理函数
        partitioner, mapSideCombine, serializer)(null)
     */

    rdd.reduceByKey(_+_)  // wordcount
    rdd.aggregateByKey(0)(_+_, _+_)   // wordcount
    rdd.foldByKey(0)(_+_)   // wordcount
    rdd.combineByKey(v=>v, (x:Int,y)=>x+y, (x:Int,y:Int)=>x+y)    // wordcount
    
    sc.stop()

  }
}
