package chapter07

import scala.collection.mutable.ListBuffer

/**
 * @author name 婉然从物
 * @create 2023-10-28 20:06
 */
object Test05_ListBuffer {
  def main(args: Array[String]): Unit = {
    // 1. 创建可变列表
    val list1: ListBuffer[Int] = new ListBuffer[Int]()
    val list2 = ListBuffer(11, 22, 33)
    println(list1)
    println(list2)
    println("================")

    // 2. 添加元素
    list1.append(44, 55)
    list2.prepend(10)

    list1.insert(1, 15, 16)

    println(list1)
    println(list2)

    println("----------------")

    11 +=: 22 +=: list1 += 12 += 23
    println(list1)

    println("=================")

    // 3. 合并List
    val list3 = list1 ++ list2
    println(list1)
    println(list2)

    println("-----------------")

    list1 ++=: list2
    println(list1)
    println(list2)

    println("=================")

    // 4. 修改元素
    list2(2) = 88
    list2.update(0, 89)
    println(list2)
    println("=================")

    // 5. 删除元素
    list2.remove(5, 5)
    list2 -= 22
    println(list2)

  }
}
