package chapter03

/**
 * @author name 婉然从物
 * @create 2023-10-22 15:49
 */
object Test03_Practice_MulTable {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 9; j <- 1 to i) {
      print(s"$j * $i = ${i * j} \t")
      if (i == j)
        println()
    }
  }

}
