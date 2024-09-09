package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-26 20:48
 */
object AnnoymousClass {
  def main(args: Array[String]): Unit = {
    val student = new Person10 {
      override val name: String = "student"

      override def eat: Unit = println("student eat")
    }

    println(student.name)
    student.eat

  }

}

abstract class Person10{
  val name:String
  def eat: Unit
}
