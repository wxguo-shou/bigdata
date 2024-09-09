package chapter03

import scala.BigDecimal
import scala.collection.immutable
import scala.math.Ordering

/**
 * @author name 婉然从物
 * @create 2023-10-22 10:26
 */
object Test02_ForLoop {
  def main(args: Array[String]): Unit = {
    // 1. 范围遍历
    for (i <- 1 to 10) {
      println(i + ". hello world")
    }

    for (i: Int <- 1.to(10)) {
      println(i + ". hello world")
    }

    println("=====================")

    for (i <- Range(1, 10)) {
      println(i + ". hello world")
    }

    for (i <- 1 until (10)) {
      println(i + ". hello world")
    }

    println("===================")
    // 2. 集合遍历
    for (i <- Array(11, 22, 33)) {
      println(i)
    }
    for (i <- Set(11, 22, 33)) {
      println(i)
    }
    for (i <- List(11, 22, 33)) {
      println(i)
    }
    println("==================================")

    // 3. 循环守卫
    for (i <- 1 to 10) {
      if (i != 5) {
        println(i)
      }
    }

    for (i <- 1 to 10 if i != 5) {
      println(i)
    }

    println("=================================")

    // 4. 循环步长
    for (i <- 1 to 10 by 2) {
      println(i)
    }

    for (i <- 10 to 1 by -1) {
      println(i)
    }

    println("----------------------------")
    for (i <- 1 to 10 reverse) {
      println(i)
    }

    for (i <- 1.0 to 10 by 0.5) {
      println(i)
    }

    for (i <- 1.0 to 10 by 0.3) {
      println(i)
    }
    println("============================")

    // 5. 循环嵌套
    for (i <- 1 to 3)
      for (j <- 1 to 3){
        println("i = " + i + ", j = " + j)
      }

    println("--------------------------")

    for (i <- 1 to 4; j <- 1 to 5){
      println("i = " + i + ", j = " + j)
    }

    println("============================")

    // 5. 循环引入变量
    for (i <- 1 to 10; j = 10 - i){
      println("i = " + i + ", j = " + j)
    }

    for {
      i <- 1 to 10
         j = 10 - i
    }
    {
      println("i = " + i + ", j = " + j)
    }

    println("============================")

    // 5. 循环返回值
    val a = for (i <- 1 to 10) {
      i
    }
    println("a = " + a)

    println("-----------------------------")
    val b = for (i <- 1 to 10) yield i * i
    println(b)

  }
}
