package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-29 9:25
 */
object Test13_SimpleFunction {
  def main(args: Array[String]): Unit = {
    val list = List(5, 1, 8, -3, 6, 4)
    val list2 = List(("a", 5), ("b", 1), ("c", 8), ("d", -3), ("e", 6), ("f", 4))

//    （1）求和
    println(list.sum)

//    （2）求乘积
    println(list.product)

//    （3）最大值
    println(list.max)
    println(list2.max)
    println(list2.maxBy( (tuple: (String, Int)) => tuple._2) )
    println(list2.maxBy( _._2 ))

//    （4）最小值
    println(list.min)
    println(list2.minBy(_._2))
    println("==================")

//    （5）排序
    // 5.1 sorted
    val sortedList = list.sorted
    println(sortedList)

    // 从大到小逆序排序
    println(list.sorted.reverse)
    // 传入隐式参数
    println(list.sorted(Ordering[Int].reverse))

    println(list2.sorted)

    println("==================")

    // 5.2 sortBy
    println(list2.sortBy(_._2))
    println(list2.sortBy(_._2)(Ordering[Int].reverse))

    // 5.3 sortWith
    println(list.sortWith( (a: Int, b: Int) => {a < b} ))
    println(list.sortWith( _ < _ ))
    println(list.sortWith( _ > _ ))
  }
}
