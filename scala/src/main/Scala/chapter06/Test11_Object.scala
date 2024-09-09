package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-26 21:28
 */
object Test11_Object {
  def main(args: Array[String]): Unit = {
//    val student = new Student11("alice", 18)
//    student.printInfo()

    val student1 = Student11.newStudent("alice", 18)
    val student2 = Student11("bob", 20)   // apply方法调用，可以直接省略apply
    student1.printInfo()
    student2.printInfo()
  }

}

// 定义类
class Student11 private (val name: String, val age: Int){
  def printInfo(): Unit = {
    println(s"student: name = ${name}, age = ${age}, school = ${Student11.school}")
  }
}

// 伴生对象
object Student11 {
  val school: String = "atguigu"

  // 定义一个类的对象实例的创建方法
  def newStudent(name:String, age: Int): Student11 = new Student11(name, age)
  def apply(name:String, age: Int): Student11 = new Student11(name, age)
}