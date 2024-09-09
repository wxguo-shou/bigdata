package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-29 8:28
 */
object Test10_Tuple {
  def main(args: Array[String]): Unit = {
    // 1. 创建元组
    val tuple: (String, Int, Char, Boolean) = ("hi", 100, 'a', false)

    // 2. 访问数据
    println(tuple._1)
    println(tuple.productElement(1))

    println("========================")

    // 3. 遍历元组数据
    for (elem <- tuple.productIterator){
      println(elem)
    }

    println("========================")

    // 4. 嵌套元组
    val mulTuple = (12, 0.3, "hi", (20, "scala"), true)
    println(mulTuple._4._2)
  }
}
