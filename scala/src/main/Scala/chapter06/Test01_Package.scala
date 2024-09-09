// 用嵌套风格定义包
package com{

  import com.atguigu.scala.Inner


  // 在外层包中定义单例对象
  object Outer{
    var out: String = "out"

    def main(args: Array[String]): Unit = {
      println(Inner.in)
    }

  }
  package atguigu{
    package scala{
      // 在内存包中定义单例对象
      object Inner{
        val in: String = "in"
        def main(args: Array[String]): Unit = {
          println(Outer.out)
          Outer.out = "outer"
          println(Outer.out)
        }
      }
    }
  }
}

package aaa{
  package bbb{

    import com.atguigu.scala.Inner

    object Test01_Package{
      def main(args: Array[String]): Unit = {
        println(Inner.in)
      }
    }
  }
}
