package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-28 15:47
 */
object Test01_ImmutableArray {
  def main(args: Array[String]): Unit = {
    // 1. 创建数组
    val arr = new Array[Int](5)
    // 另一种创建方式
    val arr2 = Array(11, 22, 33, 44, 55)

    // 2. 访问元素
    println(arr(0))
    println(arr(2))
    println(arr(3))

    arr(0) = 10
    arr(2) = 19

    println(arr(0))
    println(arr(2))
    println(arr(3))

    println("========================")

    // 3. 数组的遍历
    // 1) 普通for循环
    for (i <- 0 until arr.length) println(arr(i))
    println("----------------------- ")
    for (i <- arr.indices) println(arr(i))
    println("------------------------")

    // 2) 直接遍历所有元素， 增强for循环
    for (elem <- arr2) println(elem)
    println("------------------------")

    // 3) 迭代器
    val iter = arr2.iterator
    while (iter.hasNext) println(iter.next())
    println("------------------------")

    // 4) 调用foreach方法
    arr2.foreach( (elem: Int) => println(elem) )
    println("------------------------")
    arr2.foreach(println)
    println("------------------------")

    println(arr2.mkString(","))
    println("------------------------")

    // 4. 添加数组
    val newArr = arr2.:+(10)
    println(arr2.mkString(", "))
    println(newArr.mkString(", "))
    val newArr2 = newArr.+:(30)
    println(newArr2.mkString(", "))

    val newArr3 = newArr2 :+ 15
    println(newArr3.mkString(", "))
    val newArr4 = 5 +: 6 +: newArr3 :+ 10 :+20
    println(newArr4.mkString(", "))
  }
}
