package chapter07

import scala.collection.immutable.Queue
import scala.collection.mutable

/**
 * @author name 婉然从物
 * @create 2023-10-29 16:10
 */
object Test19_Queue {
  def main(args: Array[String]): Unit = {
    // 创建一个可变队列
    val queue: mutable.Queue[String] = new mutable.Queue[String]()

    queue.enqueue("a", "b", "c")

    println(queue)
    println(queue.dequeue())
    println(queue)
    println(queue.dequeue())
    println(queue)

    queue.enqueue("d", "e")
    println(queue.dequeue())
    println(queue)

    println("======================")

    // 不可变对列
    val queue2: Queue[String] = Queue("a", "b", "c")
    val queue3 = queue2.enqueue("d")
    println(queue2)
    println(queue3)
  }
}
