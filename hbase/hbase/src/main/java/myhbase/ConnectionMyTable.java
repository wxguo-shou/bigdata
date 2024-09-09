package myhbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author name 婉然从物
 * @create 2023-11-08 10:54
 */
public class ConnectionMyTable {
    public static Admin admin;
    public static Connection connection;

    public static Configuration configuration;
    // 创建连接对象和管理对象
    public static void init(){
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104");

        try {
            connection = ConnectionFactory.createConnection(configuration);

            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 释放连接对象和管理对象
    public static void close() {
        try {
            if (admin != null) {
                admin.close();
            }
            if (connection != null) {
                admin.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
