package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-26 11:48
 */
object Test07_Inherit {
  def main(args: Array[String]): Unit = {
    val student1 = new Student7("alice", 18)
    val student2 = new Student7("bob", 20, "1001")

    student1.printInfo()
    student2.printInfo()

    val tescher = new Tescher
    tescher.printInfo()

    def personInfo(person: Person7): Unit = {
      person.printInfo()
    }

    println("===================================")
    var person = new Person7
    personInfo(student1)
    personInfo(tescher)
    personInfo(person)
  }
}

// 定义一个父类
class Person7(){
  var name: String = _
  var age: Int = _

  println("1. 父类的主构造器调用")

  def this(name: String, age: Int) {
    this()
    println("2. 父类的辅助构造器调用")
    this.name = name
    this.age = age
  }

  def printInfo (): Unit = {
    println(s"Person: $name, $age")
  }
}

// 定义子类
class Student7(name: String, age: Int) extends Person7(name, age){
  var stdNo: String = _
  println("3. 子类的主构造器调用")

  def this(name: String, age: Int, stdNo: String) {
    this(name, age)
    println("4. 子类的辅助构造器调用")
    this.stdNo = stdNo
  }

  override def printInfo(): Unit = {
    println(s"Student: $name, $age, $stdNo")
  }
}

class Tescher extends Person7 {
  override def printInfo(): Unit = {
    println(s"Teacher")
  }
}



















