package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-27 19:17
 */
object Test14_TraitMixin {
  def main(args: Array[String]): Unit = {
    val student = new Student14
    student.study()
    student.increase()

    student.play()
    student.increase()

    student.dating()
    student.increase()

    println("========================")

    // 动态混入
    val studentWithTalent = new Student14 with Talent {
      override def singing(): Unit = println("student is good at singing")

      override def dancing(): Unit = println("student is good at dancing")
    }

    studentWithTalent.study()
    studentWithTalent.increase()
    studentWithTalent.play()
    studentWithTalent.dating()
    studentWithTalent.sayHello()

    studentWithTalent.dancing()
    studentWithTalent.singing()
  }
}

// 再定义一个特质
trait knowledge {
  var amount: Int = 0
  def increase(): Unit
}

trait Talent {
  def singing(): Unit
  def dancing(): Unit
}

class Student14 extends Person13 with Young with knowledge {
  override val name:String = "student"
  //  var age: Int = 18

  // 实现抽象方法
  def dating(): Unit = println(s"student $name is dating")

  def study(): Unit = println(s"student $name is studying")

  // 重写父类方法
  override def sayHello(): Unit = {
    super.sayHello()
    println(s"hello from: student $name")
  }

  override def increase(): Unit = {
    amount += 1
    println(s"student $name knowledge increased $amount")
  }
}