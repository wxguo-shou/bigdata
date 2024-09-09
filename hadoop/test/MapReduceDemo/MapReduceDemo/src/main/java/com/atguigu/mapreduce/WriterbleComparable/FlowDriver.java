package com.atguigu.mapreduce.WriterbleComparable;

import com.atguigu.mapreduce.partitionerwriterblecomparable.ProvincePartitioner2;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowDriver {
    public static void main(String[] args) throws Exception {
        // 1、获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2、设置jar
        job.setJarByClass(FlowDriver.class);

        // 3、关联Mapper 和Reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        // 4、设置mapper输出的key和value类型
        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);

        // 5、设置最终输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        job.setPartitionerClass(ProvincePartitioner2.class);
        job.setNumReduceTasks(5);

        // 6、设置数据的输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("E:\\output\\output2"));
        FileOutputFormat.setOutputPath(job, new Path("E:/output/output333"));

        // 7、提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
