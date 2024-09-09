package my.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author name 婉然从物
 * @create 2023-10-26 16:39
 */
public class ReadFile {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        String hdfsPath = "hdfs://hadoop102:8020";
        String user = "wanxiangguo";
        FileSystem hdfs = FileSystem.get(new URI(hdfsPath), conf, user);
        String filePath = "/test/results/part-m-00000";
        FSDataInputStream open = hdfs.open(new Path(filePath));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open));

        String line1 = bufferedReader.readLine();
        String line2 = bufferedReader.readLine();

        System.out.println(line1);
        System.out.println(line2);

        bufferedReader.close();
        open.close();
        hdfs.close();

    }
}
