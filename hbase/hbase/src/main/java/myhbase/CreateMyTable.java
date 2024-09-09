package myhbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.phoenix.shaded.org.apache.commons.configuration.CompositeConfiguration;

import java.io.IOException;

/**
 * @author name 婉然从物
 * @create 2023-11-08 10:08
 */
public class CreateMyTable {
    public static Admin admin;
    public static Connection connection;

    public static Configuration configuration;

    public static void main(String[] args) throws IOException {
        String tableName = "buyer";

        String[] columnFamily = {"reg_info"};
        init();
        create(tableName, columnFamily);
        close();


    }



    // 创建表
    public static void create(String tablename, String[]columnFamilys) throws IOException {
        TableName tableName = TableName.valueOf(tablename);

        if (admin.tableExists(tableName)){
            System.err.println("Table exists!");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            for (String str : columnFamilys) {
                tableDesc.addFamily(new HColumnDescriptor(str));
            }
            admin.createTable(tableDesc);
            System.err.println("Create Table SUCCESS!");
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

