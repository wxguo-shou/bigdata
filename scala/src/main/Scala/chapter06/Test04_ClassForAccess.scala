package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-25 20:26
 */
object Test04_ClassForAccess {

}

// 定义一个父类
class Person{
  private var idCard: String = "12345"
  protected var name: String = "alice"
  var sex:String = "female"
  private[chapter06] var age: Int = 18

  def PrintInfo() = {
    println(s"Person: $idCard $name $sex $age")
  }
}
