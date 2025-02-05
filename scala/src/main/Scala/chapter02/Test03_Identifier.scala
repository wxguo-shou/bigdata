package chapter02

/**
 * @author name 婉然从物
 * @create 2023-10-21 16:23
 */
object Test03_Identifier {
  def main(args: Array[String]): Unit = {
//    （1）以字母或者下划线开头，后接字母、数字、下划线
    val hello: String = ""
    val Hello123 = ""
    val _abc = 123

//    val h-b = ""
//    val 123abc = 234

//    （2）以操作符开头，且只包含操作符（+ - * / # !等）
    val -+/% = "hello"
    println(-+/%)

//    （3）用反引号`....`包括的任意字符串，即使是 Scala 关键字（39 个）也可以
    val `if` = "if"
    println(`if`)
  }

}
