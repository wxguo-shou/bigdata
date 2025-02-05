package chapter07

import scala.collection.mutable

/**
 * @author name 婉然从物
 * @create 2023-10-28 21:44
 */
object Test09_MutableMap {
  def main(args: Array[String]): Unit = {
    // 1. 创建map
    val map1: mutable.Map[String, Int] = mutable.Map("a" -> 13, "b" -> 25, "hello" -> 3)
    println(map1)
    println(map1.getClass)

    println("==========================")

    // 2. 添加元素
    map1.put("c", 5)
    map1.put("d", 9)
    println(map1)

    map1 += (("e", 7))
    println(map1)

    println("==========================")

    // 3. 删除元素
    println(map1("c"))
    map1.remove("c")
    println(map1.getOrElse("c", 0))

    map1 -= "d"
    println(map1)

    println("==========================")

    // 4. 修改元素
    map1.update("c", 5)
    map1.update("d", 10)
    println(map1)

    println("==========================")

    // 5. 合并两个map
    val map2: Map[String, Int] = Map("aaa" -> 11, "b" -> 29, "hello" -> 5)
    map1 ++= map2
    println(map1)
    println(map2)

//    map2 ++= map1

    println("-------------------------")

    val map3: mutable.Map[String, Int] = map1 ++ map2
    val map4: Map[String, Int] = map2 ++ map1
    println(map3)
    println(map4)
  }
}
