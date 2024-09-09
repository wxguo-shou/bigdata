package chapter02

/**
 * @author name 婉然从物
 * @create 2023-10-21 16:47
 */
object Test04_String {
  def main(args: Array[String]): Unit = {
//    （1）字符串，通过 + 号连接
    val name: String = "alice"
    val age: Int = 20
    println(age + "岁的" + name + "在尚硅谷学习")

    // * 用于将一个字符串复制多次并拼接
    println(name * 3)

//    （2）printf 用法：字符串，通过 % 传值。
    printf("%d岁的%s在尚硅谷学习\n", age, name)

//    （3）字符串模板（插值字符串）：通过$获取变量值
    printf(s"${age}岁的${name}在尚硅谷学习\n")

    val num: Double = 2.3456
    println(f"The num is ${num}%2.2f")
    println(raw"The num is ${num}%2.2f")

    // 三引号表示字符串， 保持多行字符串的原格式输出
      val sql = s"""
         |select *
         |from
         |  student
         |where
         |  name = ${name}
         |and
         |  age = ${age}
         |""".stripMargin

      println(sql)

  }

}
