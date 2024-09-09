package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-28 20:42
 */
object Test06_ImmutableSet {
  def main(args: Array[String]): Unit = {
    // 1. 创建set
    val set1 = Set(11, 22, 33, 44, 55)
    println(set1)

    println("========================")

    // 2. 添加元素
    val set2 = set1 + 13
    println(set1)
    println(set2)
    println("========================")

    // 3. 合并set
    val set3 = Set(12, 23, 35, 55, 56)
    val set4 = set2 ++ set3
    println(set2)
    println(set3)
    println(set4)
    println("========================")

    // 4. 删除元素
    val set5 = set3 - 55
    println(set3)
    println(set5)

  }
}
