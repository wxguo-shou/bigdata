//package chapter06
//
///**
// * @author name 婉然从物
// * @create 2023-10-25 19:24
// */
//object Test02_PackageObject {
//  def main(args: Array[String]): Unit = {
//    commenMethod()
//    println(commenValue)
//  }
//
//}


package chapter06{
  object Test02_PackageObject {
    def main(args: Array[String]): Unit = {
      commenMethod()
      println(commenValue)
    }

  }
}

package ccc{
  package ddd{
    object Test02_PackageObject{
      def main(args: Array[String]): Unit = {
        println(school)
      }
    }

  }


}

// 定义一个包对象
package object ccc {
  val school: String = "atguigu"
}

