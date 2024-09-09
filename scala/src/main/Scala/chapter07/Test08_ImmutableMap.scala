package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-28 21:23
 */
object Test08_ImmutableMap {
  def main(args: Array[String]): Unit = {
    val map1: Map[String, Int] = Map("a" -> 13, "b" -> 25, "hello" -> 3)
    println(map1)
    println(map1.getClass)

    println("====================")

    // 2. 遍历元素
    map1.foreach(println)
    map1.foreach( (kv: (String, Int)) => println(kv) )

    println("====================")

    // 3. 取map中所有的key 或者 value
    for (key <- map1.keys){
      println(s"$key --> ${map1.get(key)}")
    }

    println("====================")

    // 4. 访问某个key的value
    println("a: " + map1.get("a").get)
    println("c: " + map1.get("c"))
    println("c: " + map1.getOrElse("c", 0))

    println("====================")

    println(map1("a"))
  }
}
