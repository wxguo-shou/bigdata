package chapter03

import scala.util.control.Breaks
import scala.util.control.Breaks._


/**
 * @author name 婉然从物
 * @create 2023-10-22 16:59
 */
object Test06_Break {
  def main(args: Array[String]): Unit = {
    // 1. 采用抛出异常的方式， 退出循环
    try{
      for (i <- 1 until 5){
        if (i == 3)
          throw new RuntimeException
        println(i)
      }
    }catch {
      case e: Exception =>    // 什么都不做， 只是退出循环
    }

    println("这是循环外的代码")

    // 2. 使用Scala中的Breaks类的 break方法， 实现异常的抛出和捕捉
    Breaks.breakable(
      for (i <- 1 until 5){
        if (i == 3)
          Breaks.break()
        println(i)
      }
    )
    println("这是循环外的代码")

    breakable(
      for (i <- 1 until 5) {
        if (i == 3)
          break()
        println(i)
      }
    )
    println("这是循环外的代码")

  }

}
