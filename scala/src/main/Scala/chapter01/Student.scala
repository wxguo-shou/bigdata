package chapter01

/**
 * @author name 婉然从物
 * @create 2023-10-21 15:51
 */
class Student(name: String, var age: Int) {
  def printInfo(): Unit = {
    println(name + " " + age + " " + Student.school)
  }

}

//  引入伴生对象
object Student{
  val school = "atguigu"

  def main(args: Array[String]): Unit = {
    val bob = new Student("bob", 20)
    val alice = new Student("alice", 23)

    alice.printInfo()
  }
}