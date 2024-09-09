package my.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

/**
 * @author name 婉然从物
 * @create 2023-12-25 11:14
 */
public class ReadFile1 {
    public static void main(String[] args) throws IOException {
        // 创建Hadoop配置对象
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hadoop102:8020"); // 设置HDFS地址和端口号

        // 创建FileSystem对象
        FileSystem fs = FileSystem.get(conf);

        // 打开HDFS文件
        Path filePath = new Path("/test/results/part-r-00000"); // 替换为您的文件路径
        InputStream in = new BufferedInputStream(fs.open(filePath));

        // 创建本地文件夹
        File localDir = new File("./data"); // 替换为您的本地文件夹路径
        if (!localDir.exists()) {
            localDir.mkdir();
        }

        // 将文件保存到本地文件夹
        String fileName = filePath.getName(); // 获取文件名
        File localFile = new File(localDir, fileName); // 创建本地文件路径
        OutputStream out = new FileOutputStream(localFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        out.close();
        in.close();
    }
}