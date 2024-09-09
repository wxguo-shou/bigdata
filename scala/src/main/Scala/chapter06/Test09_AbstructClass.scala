package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-26 20:31
 */
object Test09_AbstructClass {
  def main(args: Array[String]): Unit = {
    val student = new Student9
    student.eat()
    student.sleep()
  }

}

// 定义抽象类
abstract class Person9{
  // 非抽象属性
  val name: String = "person"

  // 抽象属性
  var age: Int

  // 非抽象方法
  def eat() = {
    println("person eat")
  }

  // 抽象方法
  def sleep(): Unit
}

// 定义具体的实现子类
class Student9 extends Person9 {
  // 实现抽象属性和方法
  var age: Int = 18

  override def sleep(): Unit = {
    println("student sleep")
  }

  // 重写非抽象属性和方法
  override val name: String = "student"   // 不能重写var属性， 直接修改

  override def eat(): Unit = {
    super.eat()
    println("student eat")
  }
}










