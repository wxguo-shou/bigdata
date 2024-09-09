package chapter02

/**
 * @author name 婉然从物
 * @create 2023-10-21 22:04
 */
object Test09_Problem_DataTypeConversion {
  def main(args: Array[String]): Unit = {
    /*
    原码: 0000 0000 0000 0000 0000 0000 1000 0010
    补码: 0000 0000 0000 0000 0000 0000 1000 0010
    截取最后一个字节,Byte
     得到补码: 1000 0010
     原码: 1111 1101
    表示负数: -126
     */
    val n: Int = 130
    val b: Byte = n.toByte
    println(b)
  }

}
