package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-28 19:27
 */
object Test04_List {
  def main(args: Array[String]): Unit = {
    // 1. 创建一个List
    val list1 = List(11, 22, 33)
    println(list1)

    // 2. 访问和遍历元素
    println(list1(1))
//    list1(1) = 12
    list1.foreach(println)

    // 3. 添加元素
    val list2 = 10 +: list1
    val list3 = list1 :+ 11
    println(list1)
    println(list2)
    println(list3)

    println("====================")

    val list4 = list2.::(30)
    println(list4)

    val list5 = Nil.::(10)
    val list6 = 30 :: 29 :: Nil
    val list7 = 10 :: 20 :: 30 :: Nil
    println(list5)
    println(list6)
    println(list7)

    println("=========================")

    // 4. 合并列表
    val list8 = list6 :: list7
    println(list8)

    val list9 = list6 ::: list7
    val list10 = list6 ++ list7
    println(list9)
    println(list10)
  }
}
