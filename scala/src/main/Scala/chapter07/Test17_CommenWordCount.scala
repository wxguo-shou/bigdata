package chapter07

/**
 * @author name 婉然从物
 * @create 2023-10-29 14:49
 */
object Test17_CommenWordCount {
  def main(args: Array[String]): Unit = {
    val stringList: List[String] = List(
      "hello",
      "hello world",
      "hello scala",
      "hello spark from scala",
      "hello flink from scala"
    )

    // 1. 对字符串进行切分， 得到一个打散所有单词的列表
//    val wordList1 = stringList.map(_.split(" "))
//    val wordList2 = wordList1.flatten
//    println(wordList2)

    val wordList = stringList.flatMap(_.split(" "))
    println(wordList)

    // 2. 相同的单词进行分组
    val groupMap: Map[String, List[String]] = wordList.groupBy(word => word)

    // 3. 对分组之后的list取长度， 得到每个单词的个数
    val countMap = groupMap.map(kv => (kv._1, kv._2.length))
//    println(countMap)

    // 4. 将map转化为list， 并取前三名
    val sortList = countMap.toList
      .sortWith(_._2 > _._2)
      .take(3)

    println(sortList)
  }
}
