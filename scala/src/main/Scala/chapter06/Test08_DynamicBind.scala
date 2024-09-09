package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-26 20:10
 */
object Test08_DynamicBind {
  def main(args: Array[String]): Unit = {
    // java中 属性是静态绑定的， 在Scala中， 属性和方法都是动态绑定
    val student: Person8 = new Student8
    println(student.name)
    student.hello()

  }

}

class Person8 {
  val name: String = "person"
  def hello() = {
    println("hello person")
  }
}

class Student8 extends Person8 {
  override val name: String = "student"
  override def hello(): Unit = {
    println("hello student")
  }
}
