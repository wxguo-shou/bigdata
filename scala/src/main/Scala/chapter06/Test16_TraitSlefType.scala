package chapter06

/**
 * @author name 婉然从物
 * @create 2023-10-27 20:35
 */
object Test16_TraitSlefType {
  def main(args: Array[String]): Unit = {
    val user = new RegisterUser("alice", "123456")
    user.insert()
  }
}

// 用户类
class User(val name: String, val password: String)

trait UserDao {
  _: User =>

  // 向数据库插入数据
  def insert(): Unit = {
    println(s"insert into bd: ${this.name}")
  }
}

// 定义用户注册类
class RegisterUser(name: String, password: String) extends User(name, password) with UserDao