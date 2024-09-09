package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-27 11:14
 */
object Test12_Singleton {
  def main(args: Array[String]): Unit = {
    val student1 = Student12.getInstance()
    val student2 = Student12.getInstance()
    student1.printInfo()
    student2.printInfo()
    println(student1)
    println(student2)

  }

}

class Student12 private (val name: String, val age: Int){
  def printInfo(): Unit = {
    println(s"student: name = ${name}, age = ${age}, school = ${Student11.school}")
  }
}

// 饿汉式
//object Student12 {
//  private val student: Student12 = new Student12("alice", 18)
//  def getInstance(): Student12 = student
//}

// 懒汉式
object Student12 {
  private var student: Student12 = _
  def getInstance(): Student12 = {
    if (student == null) {
      student = new Student12("alice", 18)
    }
    student
  }
}