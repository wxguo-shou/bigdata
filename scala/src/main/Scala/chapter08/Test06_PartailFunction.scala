package chapter08

/**
 * @author name 婉然从物
 * @create 2023-10-29 20:27
 */
object Test06_PartailFunction {
  def main(args: Array[String]): Unit = {
    val list: List[(String, Int)] = List(("a", 12), ("b", 25), ("c", 13), ("a", 15))

    // 1. map转换， 实现key不变， value变为2倍
    val newList = list.map(tuple => (tuple._1, tuple._2 * 2))

    // 2. 用模式匹配对元组进行赋值， 实现功能
    val newList2 = list.map(
      tuple => {
        tuple match {
          case (word, count) => (word, count * 2)
        }
      }
    )

    // 3. 省略lambda表达式的写法， 进行简化
    val newList3 = list.map { case (word, count) => (word, count * 2) }

    println(newList)
    println(newList2)
    println(newList3)

    // 偏函数的应用， 求绝对值
    // 对输入数据分为不同的情形： 正、 负、 0
    val positiveAbs: PartialFunction[Int, Int] = {
      case x if x > 0 => x
    }
    val negativeAbs: PartialFunction[Int, Int] = {
      case x if x < 0 => -x
    }
    val zeroAbs: PartialFunction[Int, Int] = {
      case 0 => 0
    }

    def abs(x: Int): Int = (positiveAbs orElse negativeAbs orElse zeroAbs) (x)

    println(abs(-2))
    println(abs(3))
    println(abs(0))

  }
}
