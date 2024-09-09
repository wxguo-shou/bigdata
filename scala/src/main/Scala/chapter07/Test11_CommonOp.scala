package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-29 8:41
 */
object Test11_CommonOp {
  def main(args: Array[String]): Unit = {
    val list = List(11, 22, 23, 89, 100)
    val set = Set(33, 34, 52, 28, 38)
//    （1）获取集合长度
    println(list.length)
    println("=====================")

//    （2）获取集合大小
    println(set.size)
    println("=====================")

//    （3）循环遍历
    for (elem <- list) println(elem)
    println("=====================")
    set.foreach(println)
    println("=====================")

//    （4）迭代器
    for (elem <- list.iterator) println(elem)
    println("=====================")

//    （5）生成字符串
    println(list)
    println(set)
    println(list.mkString("--"))
    println("=====================")

//    （6）是否包含
    println(list.contains(23))
    println(set.contains(23))
  }
}
