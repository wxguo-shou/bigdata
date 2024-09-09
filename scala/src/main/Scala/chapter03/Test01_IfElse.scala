package chapter03

import scala.io.StdIn

/**
 * @author name 婉然从物
 * @create 2023-10-22 9:57
 */
object Test01_IfElse {
  def main(args: Array[String]): Unit = {
    println("请输入您的年龄: ")
    val age: Int = StdIn.readInt()

    // 分支语句的返回值
    val result: Any = if (age <= 6){
      println("童年")
      "童年"
    } else if (age < 18){
      println("青少年")
      "青少年"
    } else if (age < 35){
      println("青年")
      age
    } else if (age < 60){
      println("中年")
      age
    } else {
      println("老年")
      age
    }
    println("result: " + result)

    // java 中三元运算符 a ? b : c

    val res: String = if (age >= 18){
      "成年"
    } else {
      "未成年"
    }

    val res2 = if (age >= 18) "成年" else "未成年"

  }

}
