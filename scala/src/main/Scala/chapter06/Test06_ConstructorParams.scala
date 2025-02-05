package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-26 11:03
 */
object Test06_ConstructorParams {
  def main(args: Array[String]): Unit = {
    val student2 = new Student2
    student2.age = 18
    student2.name = "alice"
    println(s"student2: name = ${student2.name}, age = ${student2.age}")

    val student3 = new Student3("bob", 20)
    println(s"student3: name = ${student3.name}, age = ${student3.age}")

    val student4 = new Student4("cary", 25)
//    println(s"student4: name: ${student4.name}, age: ${student4.age}")
    student4.printInfo()

    val student5 = new Student5("bob", 20)
    println(s"student5: name = ${student5.name}, age = ${student5.age}")
    //    student5.age = 21   属性不能被修改

    val student6 = new Student6("cary", 25, "atguigu")
    println(s"student6: name = ${student6.name}, age = ${student6.age}, school = ${student6.school}")
    student6.printInfo()


  }

}

// 定义类
// 无参构造器
class Student2 {
  // 单独定义属性
  var name: String = _
  var age: Int = _
}

// 上面定义等价于
class Student3 (var name: String, var age: Int)

// 主构造参数无修饰
class Student4 (name: String, age: Int){
  def printInfo(): Unit = {
    println(s"student4: name = $name, age = $age")
  }
}

//class Student4 (_name: String, _age: Int){
//  var name: String = _name
//  var age: Int = _age
//}

class Student5 (val name: String, val age: Int)

class Student6 (val name: String, val age: Int){
  var school: String = _

  def this (name: String, age: Int, school: String) {
    this(name, age)
    this.school = school
  }

  def printInfo() {
    println(s"student6: name = $name, age = $age, school = $school")
  }
}





















