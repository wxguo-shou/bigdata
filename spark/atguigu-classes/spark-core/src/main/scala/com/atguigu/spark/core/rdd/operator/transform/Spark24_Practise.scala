package com.atguigu.spark.core.rdd.operator.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author name 婉然从物
 * @create 2023-11-09 8:49
 */
object Spark24_Practise {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")

    val sc = new SparkContext(sparkConf)

    // 案例实操
    // 1. 获取原始数据:时间戳，省份，城市，用户，广告
    val rdd = sc.textFile("atguigu-classes/datas/agent.log")

    // 2. 将原始数据进行结构的转换。方便统计
    //    时间戳，省份，城市，用户，广告
    //      => ( ( 省份，广告 )，1 )
    val mapRDD = rdd.map(
      line => {
        val datas = line.split(" ")
        ((datas(1), datas(4)), 1)
      }
    )

    // 3. 将转换结构后的数据，进行分组聚合
    //    ( ( 省份，广告 )，1)=>( ( 省份，广告 )，sum
    val reduceRDD = mapRDD.reduceByKey(_ + _)

    // 4. 将聚合的结果进行结构的转换
    //    ((省份，广告 )，sum ) => (省份，( 广告，sum))
    val mapRDD1 = reduceRDD.map {
      case ((province, advertisement), sum) => (province, (advertisement, sum))
    }

    // 5. 将转换结构后的数据根据省份进行分组(省份 ，[(广告A ，sumA ) ，(广告B ，sumB )] )
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = mapRDD1.groupByKey()

    // 6. 将分组后的数据组内排序 ( 降序 ) ，取前3名
    val sortRDD = groupRDD.mapValues(
      iter => {
        iter.toList.sortWith(_._2 > _._2).take(3)
      }
    )

    // 7. 采集数据打印在控制台
    sortRDD.collect().foreach(println)


    sc.stop()

  }
}
