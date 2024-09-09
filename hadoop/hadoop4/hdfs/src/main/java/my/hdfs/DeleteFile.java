package my.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author name 婉然从物
 * @create 2023-10-26 17:08
 */
public class DeleteFile {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        String hdfsPath = "hdfs://hadoop102:8020";
        String user = "wanxiangguo";
        FileSystem hdfs = FileSystem.get(new URI(hdfsPath), conf, user);
        String filePath = "/hdfs/test05/hdfs05new.txt";
        boolean delete = false;
        boolean result = hdfs.delete(new Path(filePath), delete);
        System.out.println(result);
    }
}
