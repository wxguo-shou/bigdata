package chapter05

/**
 * @author name 婉然从物
 * @create 2023-10-23 21:13
 */
object Test08_Practice {
  def main(args: Array[String]): Unit = {
    val fun = (a: Int, b: String, c: Char) => {if (a == 0 && b == "" && c == '0') false else true}
      println(fun(0, "", '0'))
      println(fun(0, "", '1'))
      println(fun(0, "i", '0'))
    println("===========================")

    def func(a: Int): String=>Char=>Boolean = {
      def f1(b: String): Char=>Boolean = {
        def f2(c: Char): Boolean = {
          if(a == 0 && b == "" && c == '0')
            false
          else
            true
        }
      f2
     }
    f1
    }

    println(func(0)("")('0'))
    println(func(1)("")('0'))

    // 函数简写
    def func1(a: Int): String => Char => Boolean = {
      b => c=> if (a == 0 && b == "" && c == '0') false else true
    }

    println(func1(0)("")('0'))
    println(func1(1)("")('0'))

    // 柯里化
    def fun2(a: Int)(b: String)(c: Char): Boolean = {
      if (a == 0 && b == "" && c == '0') false else true
    }

    println(func1(0)("")('0'))
    println(func1(1)("")('0'))
  }

}
