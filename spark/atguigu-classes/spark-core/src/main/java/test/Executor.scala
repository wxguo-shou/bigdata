package test

import java.io.{InputStream, ObjectInputStream}
import java.net.{ServerSocket, Socket}

/**
 * @author name 婉然从物
 * @create 2023-10-31 19:41
 */
object Executor {
  def main(args: Array[String]): Unit = {
    // 启动服务器，接受数据
    val server = new ServerSocket(9999)
    println("服务器启动， 等待客户端的连接")

    // 等待客户端的连接
    val client: Socket = server.accept()

    val in: InputStream = client.getInputStream
    val objIn = new ObjectInputStream(in)

    val task: Task = objIn.readObject().asInstanceOf[Task]

    val ints = task.compute()
    println("计算结点计算的结果为： " + ints)

    objIn.close()
    client.close()
    server.close()
  }
}
