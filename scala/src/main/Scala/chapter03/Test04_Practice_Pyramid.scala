package chapter03

/** 九层妖塔
 * @author name 婉然从物
 * @create 2023-10-22 16:11
 */
object Test04_Practice_Pyramid {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 9){
      println(" " * (9 - i) + "*" * (2 * i - 1))
    }
  }

}
