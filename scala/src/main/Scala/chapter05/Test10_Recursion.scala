package chapter05

/**
 * @author name 婉然从物
 * @create 2023-10-24 19:14
 */
object Test10_Recursion {
  def main(args: Array[String]): Unit = {
    println(fact(5))

    println(tailFact(5))

  }

  def fact(n: Int): Int = {
    if (n == 0) return 1;
    n * fact(n - 1)
  }

  // 尾递归实现
  def tailFact(n: Int): Int = {
    def loop(n: Int, currRes: Int): Int = {
      if (n == 0) return currRes
      loop(n - 1, currRes * n)
    }
    loop(n, 1)
  }

}
