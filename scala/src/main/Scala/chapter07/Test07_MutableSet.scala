package chapter07

import scala.collection.mutable

/**
 * @author name 婉然从物
 * @create 2023-10-28 20:58
 */
object Test07_MutableSet {
  def main(args: Array[String]): Unit = {
    // 1. 创建set
    val set1: mutable.Set[Int] = mutable.Set(13, 27, 38, 45, 92, 38)
    println(set1)

    println("==============")

    // 2. 添加元素
    val set2 = set1 + 11
    println(set1)
    println(set2)

    println("--------------")

    set1 += 11
    println(set1)

    val flag1 = set1.add(10)
    println(set1)
    println(flag1)
    val flag2 = set1.add(10)
    println(set1)
    println(flag2)

    println("==============")

    // 3. 删除元素
    set1 -= 11
    println(set1)

    val flag3 = set1.remove(10)
    println(set1)
    println(flag3)
    val flag4 = set1.remove(10)
    println(set1)
    println(flag4)

    println("==============")

    // 4. 合并两个Set
    val set3 = mutable.Set(13, 28, 38, 88, 92, 96)
    println(set1)
    println(set3)

    println("--------------")

    val set4 = set1 ++ set3
    println(set1)
    println(set3)
    println(set4)

    println("--------------")

    set3 ++= set1
    println(set1)
    println(set3)
  }
}
