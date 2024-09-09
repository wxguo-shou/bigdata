package chapter05

/**
 * @author name 婉然从物
 * @create 2023-10-24 16:46
 */
object Test09_ClosureAndCurrying {
  def main(args: Array[String]): Unit = {
    def add(a: Int, b: Int): Int = {
      a + b
    }
  }

  // 1. 考虑固定一个加数的场景
  def addByFour(b: Int): Int = {
    4 + b
  }

  // 2. 扩展固定加数改变的情况
  def addByFive(b: Int): Int = {
    5 + b
  }

  // 3. 将固定加数作为另一个参数传入， 但是是作为“第一层参数”传入
  def addByA(a: Int): Int=>Int = {
    def addB(b: Int): Int = {
      a + b
    }
    addB
  }

  println(addByA(20)(13))

  val addByFour2 = addByA(4)
  val addByFive2 = addByA(5)

  println(addByFour2(10))
  println(addByFive2(10))

  // Lambda表达式简写
  def addByA2(a: Int): Int => Int = a + _

  println(addByA2(10)(20))

  // 柯里化
  def addCurrying(a: Int)(b: Int): Int = {
    a + b
  }
  println(addCurrying(10)(20))
}
