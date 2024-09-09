package chapter05

/**
 * @author name 婉然从物
 * @create 2023-10-24 21:18
 */
object Test13_Lazy {
  def main(args: Array[String]): Unit = {
    lazy val result = sum(1, 2)
    println("1. 函数调用")
    println("2. result= " + result)
    def sum(a: Int, b: Int): Int = {
      println("3. 函数调用")
      a + b
    }
  }

}
