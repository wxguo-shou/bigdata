package bigwork;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MyDriver {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        // 1、获取job
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf);

        // 2、设置jar包路径
        job.setJarByClass(MyDriver.class);

        // 3、关联mapper和reducer
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);

        // 4、设置map输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        // 5、设置最终的kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

//        job.setNumReduceTasks(0);     // 设置reduceTask的个数

        // 6、设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("hdfs://hadoop102:8020/test/data/comments_data.csv"));
        FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop102:8020/test/results"));

        // 7、提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);

    }

}
