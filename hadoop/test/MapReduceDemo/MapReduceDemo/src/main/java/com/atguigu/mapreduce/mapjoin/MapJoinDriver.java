package com.atguigu.mapreduce.mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MapJoinDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, URISyntaxException {
        // 1、获取job信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2、设置加载jar包路径
        job.setJarByClass(MapJoinDriver.class);

        // 3、关联Mapper
        job.setMapperClass(MapJoinMapper.class);

        // 4、设置Map输出KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        // 5、设置最终输出KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 加载缓存资源
        job.addCacheFile(new URI("file:///E:/input/tablecache/pd.txt"));

        // Map端的Join逻辑不需要Reducer阶段， 设置ReducerTask任务为0
        job.setNumReduceTasks(0);

        // 6、设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path("E:\\input\\inputtable2"));
        FileOutputFormat.setOutputPath(job, new Path("E:/output/output1"));

        // 7、提交job
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);

    }
}
