package myhbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author name 婉然从物
 * @create 2023-11-08 10:38
 */
public class DeleteMyTable {
    public static Admin admin;
    public static Connection connection;

    public static Configuration configuration;
    public static void main(String[] args) throws IOException {
        String tableName = "mytb";
        init();
        delete(tableName);
        close();

    }

    // 删除表
    public static void delete(String tablename) throws IOException {
        TableName tableName = TableName.valueOf(tablename);
        if (admin.tableExists(tableName)){
            try {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
                System.err.println("Delete table Success");
            } catch (IOException e) {
                System.err.println("Delete Table Failed");
            }
        } else {
            System.err.println("table not exists");
        }
    }

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
