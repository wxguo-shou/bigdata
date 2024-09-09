package chapter08

/**
 * @author name 婉然从物
 * @create 2023-10-29 20:20
 */
object Test05_MatchCaseClass {
  def main(args: Array[String]): Unit = {
    val student = Student1("alice", 18)

    // 针对对象实例内容进行匹配
    val result = student match {
      case Student1("alice", 18) => "Alice, 18"
      case _ => "Else"
    }

    println(result)
  }
}

case class Student1(name: String, age: Int)