package com.atguigu.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


public class fsClient {

    private FileSystem fs;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        //连接集群nn地址
        URI uri = new URI("hdfs://hadoop102:8020");
        //创建一个配置文件
        Configuration configuration = new Configuration();

        configuration.set("dfs.replication", "2");

        String user = "wanxiangguo";
        //获取到一个客户端对象
        fs = FileSystem.get(uri, configuration, user);

    }

    @After
    public void close() throws IOException {
        // 关闭资源
        fs.close();
    }

    @Test
    public void testmkdir() throws URISyntaxException, IOException, InterruptedException {

        //创建一个文件夹
        fs.mkdirs(new Path("/xiyou/huaguoshan1"));

    }

    /**
     * 参数优先级：代码里面的配置 > 项目资源目录下的配置文件 > hdfs-site.xml > hdfs-default.xml
     *
     * @throws IOException
     */
    //上传
    @Test
    public void testPut() throws IOException {
        //参数解读： 参数一：是否删除元数据， 参数二：是否覆盖内容  参数三：原数据路径  参数四：目标路径
        fs.copyFromLocalFile(false, true, new Path("C:\\Users\\郭万像\\Desktop\\sunwukong.txt"), new Path("hdfs://hadoop102/xiyou/huaguoshan/"));
    }

    @Test
    public void testGet() throws IOException {
        //参数解读： 参数一：原文件是否删除,true为删除， 参数二：原文件路径HDFS， 参数三：目标地址路径Win， 参数四：数据校验，false为需要校验
        fs.copyToLocalFile(false, new Path("hdfs://hadoop102/xiyou/huaguoshan"), new Path("E:\\"), true);
    }

    @Test
    public void testRm() throws IOException {
        //参数一： 删除路径，  参数二：是否递归删除

        //fs.delete(new Path("/jdk-8u144-linux-x64.tar.gz"), false);  删除文件

        //fs.delete(new Path("/xiyou"), false); 删除空目录

        fs.delete(new Path("/jinguo"), true);//删除非空目录
    }

    @Test
    public void testMv() throws IOException {
        //参数一：原文件路径，参数额：目标文件路径
        //对文件名称的修改
        //fs.rename(new Path("/input/word.txt"),new Path("/input/ss.txt"));

        //文件的移动和更名
        //fs.rename(new Path("/input/ss.txt"), new Path("/cls.txt"));

        //目录更名
        fs.rename(new Path("/input"), new Path("/output"));

    }

    //获取文件详细信息
    @Test
    public void fileDetail() throws IOException {
        //获取所有文件信息
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println("==========" + fileStatus.getPath() + "===========");
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());
            //获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println(Arrays.toString(blockLocations));
        }
    }

    @Test
    public void testFile() throws IOException {
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus status : listStatus) {
            if (status.isFile()) {
                System.out.println("文件： " + status.getPath().getName());
            } else {
                System.out.println("目录： " + status.getPath().getName());
            }
        }

    }

}
