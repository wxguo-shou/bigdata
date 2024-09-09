package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-29 9:00
 */
object Test12_DerivedCollection {

    def main(args: Array[String]): Unit = {
      val list1 = List(12, 32, 56, 31, 53)
      val list2 = List(56, 43, 53, 92, 12)
      //    （1）获取集合的头
      println(list1.head)

      //    （2）获取集合的尾（不是头的就是尾）
      println(list1.tail)

      //    （3）集合最后一个数据
      println(list2.last)

      //    （4）集合初始数据（不包含最后一个）
      println(list2.init)

      //    （5）反转
      println(list1.reverse)

      //    （6）取前（后）n 个元素
      println(list1.take(3))
      println(list1.takeRight(3))

      //    （7）去掉前（后）n 个元素
      println(list1.drop(3))
      println(list1.dropRight(3))

      println("======================")

      //    （8）并集
      val union = list1.union(list2)
      println("union: " + union)
      println(list1 ::: list2)
      println("======================")

      // Set并集， 会去重
      val set1 = Set(12, 32, 56, 31, 53)
      val set2 = Set(56, 43, 53, 92, 12)
      val union2 = set1.union(set2)
      println("union2: " + union2)
      println(set1 ++ set2)
      println("======================")

      //    （9）交集
      val intersection = list1.intersect(list2)
      println("intersection: " + intersection)
      println("======================")

      //    （10）差集
      val diff1 = list1.diff(list2)
      val diff2 = list2.diff(list1)
      println("diff1: " + diff1)
      println("diff2: " + diff2)
      println("======================")

      //    （11）拉链
      println("zip: " + list1.zip(list2))
      println("zip: " + list2.zip(list1))
      println("======================")

      //    （12）滑窗
      for (elem <- list1.sliding(3)) println(elem)
      println("======================")

      for (elem <- list1.sliding(3, 2)) println(elem)

      for (elem <- list1.sliding(3, 3)) println(elem)   // 滚动窗口

    }


}
