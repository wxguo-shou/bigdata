package chapter06

import scala.beans.BeanProperty

/**
 * @author name 婉然从物
 * @create 2023-10-25 19:47
 */
object Test03_Class {
  def main(args: Array[String]): Unit = {
    // 创建一个对象
    val student = new Student()
//    student.name
    println(student.age)
    println(student.sex)
    student.sex = "female"
    println(student.sex)
  }
}

// 定义一个类
class Student{
  // 定义属性
  private var name: String = "alice"
  @BeanProperty
  var age: Int = _
  var sex: String = _
}
