package myhbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * @author name 婉然从物
 * @create 2023-11-08 10:57
 */
public class InsertDataToMyTable {
    public static Admin admin;
    public static Connection connection;

    public static Configuration configuration;

    public static void main(String[] args) throws IOException {
        init();
        insertData("buyer", "20358", "reg_info", "注册日期", "2010-05-04");
        insertData("buyer", "20358", "reg_info", "注册IP", "124.64.242.30");
        insertData("buyer", "20358", "reg_info", "卖家状态", "1");

        insertData("buyer", "20356", "reg_info", "注册日期", "2010-05-05");
        insertData("buyer", "20356", "reg_info", "注册IP", "117.136.0.172");
        insertData("buyer", "20356", "reg_info", "卖家状态", "0");

        insertData("buyer", "20357", "reg_info", "注册日期", "2010-05-06");
        insertData("buyer", "20357", "reg_info", "注册IP", "114.94.44.230");
        insertData("buyer", "20357", "reg_info", "卖家状态", "1");

        close();
    }

    public static void insertData(String tableName, String rowKey, String colFamily, String col, String val) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
        table.put(put);
        table.close();
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
