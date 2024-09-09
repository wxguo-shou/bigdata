package com.atguigu;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author name 婉然从物
 * @create 2023-10-30 19:22
 */
public class PhoenixConnection {
    public static void main(String[] args) throws SQLException, IOException {
        // 标准的jdbc连接
        // 1. url
        String url = "jdbc:phoenix:hadoop102,hadoop103,hadoop104:2181";

        // 2. 配置对象  没有用户名和密码
        Properties properties = new Properties();

        // 3. 获取连接
        Connection connection = DriverManager.getConnection(url, properties);

        // 4. 编译sql  查询语句不能写分号
        PreparedStatement preparedStatement = connection.prepareStatement("select * from student");
        
        // 5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        // 6. 打印结果
        while (resultSet.next()){
            System.out.println(resultSet.getString(1) + ":" + resultSet.getString(2) + ":"
                    + resultSet.getLong(3) + ":" + resultSet.getString(4));
        }

        // 7. 关闭连接
        connection.close();

        // 关闭之前写的类单例连接
//        HbaseConnection.closeConnection();

    }
}
