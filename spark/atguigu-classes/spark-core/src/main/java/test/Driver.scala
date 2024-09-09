package test

import java.io.ObjectOutputStream
import java.net.Socket


/**
 * @author name 婉然从物
 * @create 2023-10-31 19:40
 */
object Driver {
  def main(args: Array[String]): Unit = {
    // 连接服务器
    val client = new Socket("localhost", 9999)

    val out = client.getOutputStream
    val objOut = new ObjectOutputStream(out)

    val task = new Task

    objOut.writeObject(task)
    objOut.flush()
    objOut.close()
    client.close()

    println("客户端数据发送完毕")
  }
}
