package chapter07

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * @author name 婉然从物
 * @create 2023-10-28 16:40
 */
object Test02_ArrayBuffer {
  def main(args: Array[String]): Unit = {
    // 1. 创建可变数组
    val arr1: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    val arr2 = ArrayBuffer(11, 22, 33, 44, 55)

    println(arr1)
    println(arr2)

    // 2. 访问元素
//    println(arr1(0))    // error
    println(arr2(2))
    arr2(2) = 23
    println(arr2(2))
    println("========================")

    // 3. 添加元素
    val newArr1 = arr1 :+ 15
    println(newArr1)
    println(arr1)
    println(arr1 == newArr1)

    val newArr2 = arr1 += 19
    println(arr1)
    println(newArr2)
    println(arr1 == newArr2)
    newArr2 += 13
    println(arr1)

    77 +=: arr1
    println(arr1)
    println(newArr2)

    arr1.append(36)
    arr1.prepend(23,12)
    arr1.insert(1, 11, 12)
    println(arr1)

    arr1.insertAll(2, newArr1)
    arr1.prependAll(newArr2)
    println(arr1)

    println("=======================")

    // 4. 删除元素
    arr1.remove(2)
    println(arr1)
    arr1.remove(0, 10)
    println(arr1)

    arr1 -= 12
    println(arr1)
    arr1 -= 14
    println(arr1)
    println("========================")

    // 5. 可变数组转化成不可变数组
    val arr: ArrayBuffer[Int] = ArrayBuffer(11, 22, 33)
    val newArr: Array[Int] = arr.toArray
    println(newArr.mkString(", "))
    println(arr)

    // 6. 不可变数组转换成可变数组
    val buffer: mutable.Buffer[Int] = newArr.toBuffer
    println(buffer)
    println(newArr)
  }
}
