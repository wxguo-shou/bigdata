package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-28 17:36
 */
object Test03_MulArray {
  def main(args: Array[String]): Unit = {
    // 1. 创建二维数组
    val array: Array[Array[Int]] = Array.ofDim[Int](2, 3)

    // 2. 访问元素
    array(0)(2) = 10
    array(1)(0) = 3
    println(array.mkString(", "))

    for (i <- 0 until array.length; j <- 0 until array(i).length){
      println(array(i)(j))
    }

    for (i <- array.indices; j <- array(i).indices) {
      print(array(i)(j) + "\t")
      if (j == array(i).length - 1)
        println()
    }

    array.foreach(line => line.foreach(println))

    array.foreach(_.foreach(println))
  }
}
