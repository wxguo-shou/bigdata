package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-27 19:37
 */

/*
    抽象类和特质的区别
    1. 特质可扩展性强， 优先使用
    2. 抽象类可以传参数
 */
object Test15_TraitOverlying {
  def main(args: Array[String]): Unit = {
    val student = new Student15
    student.increase()

    // 钻石问题特征叠加
    val myFootBall = new MyFootBall
    println(myFootBall.describe())
  }

}

// 定义球类特征
trait Ball {
  def describe(): String = "ball"
}

// 定义颜色特征
trait ColorBall extends Ball {
  var color: String = "red"
  override def describe(): String = color + "-" + super.describe()
}

// 定义种类特征
trait CategoryBall extends Ball {
  var category: String = "foot"
  override def describe(): String = category + "-" + super.describe()
}

// 定义一个自定义球的类
class MyFootBall extends CategoryBall with ColorBall {
  override def describe(): String = "my ball is a " + super[CategoryBall].describe()
}

trait Knowledge15 {
  var amount: Int = 0
  def increase(): Unit = {
    println("knowledge increased")
  }
}

trait Talent15 {
  def singing(): Unit
  def dancing(): Unit
  def increase(): Unit = {
    println("talent increased")
  }
}

class Student15 extends Person13 with Knowledge15 with Talent15 {
  override def singing(): Unit = println("dancing")

  override def dancing(): Unit = println("singing")

  override def increase(): Unit = {
    // 调用最后一个特质
    super.increase()
  }
}