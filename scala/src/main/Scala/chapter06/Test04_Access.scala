package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-25 20:22
 */
object Test04_Access {
  def main(args: Array[String]): Unit = {
    // 创建对象
    val person: Person = new Person()
//    person.idCard   // error
//    person.name   // error
    println(person.age)
    println(person.sex)

    person.PrintInfo()

    val worker: Worker = new Worker()
    worker.PrintInfo()
  }

}

// 定义一个子类
class Worker extends Person{
  override def PrintInfo(): Unit = {
//    println(idCard)   // error
    name = "bob"
    age = 15
    sex = "male"
    println(s"Worker: $name $sex $age")

  }
}
