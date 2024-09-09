package myhbase;

import com.sun.org.apache.xml.internal.security.Init;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * @author name 婉然从物
 * @create 2023-11-08 11:05
 */
public class getDataFromMyTable {
    public static Admin admin;
    public static Connection connection;

    public static Configuration configuration;

    public static void main(String[] args) throws Exception {
        init();

        getData("buyer", "20357");

        close();

    }


//    public static void getData(String tableName, String rowKey, String colFamily, String col) throws IOException{
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        Get get = new Get(rowKey.getBytes());
//        get.addColumn(colFamily.getBytes(), col.getBytes());
//        Result result = table.get(get);
//        System.out.println(new String(result.getValue(colFamily.getBytes(), col==null?null:col.getBytes())));
//        table.close();
//    }

//    public static void getData(String tableName, String rowKey, String colFamily, String col) throws IOException{
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        Get get = new Get(rowKey.getBytes());
//        get.addColumn(colFamily.getBytes(), col.getBytes());
//        Result result = table.get(get);
//        byte[] value = result.getValue(colFamily.getBytes(), col == null ? null : col.getBytes());
//        System.out.println(value == null ? "No value found" : new String(value));
//        table.close();
//    }

    public static void getData(String tableName, String rowKey) throws Exception {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        Result result = table.get(get);

        if (result != null && !result.isEmpty()) {
            System.out.println("查询到的数据： " + result);
        } else {
            System.out.println("未查询到数据");
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
