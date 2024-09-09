package chapter07

import scala.collection.immutable

/**
 * @author name 婉然从物
 * @create 2023-10-29 16:22
 */
object Test20_Parallel {
  def main(args: Array[String]): Unit = {
    val result: immutable.IndexedSeq[Long] = (1 to 100).map(
      x => Thread.currentThread.getId
    )
    println(result)

    val result2 = (1 to 100).par.map(
      x => Thread.currentThread.getId
    )
    println(result2)
  }
}
