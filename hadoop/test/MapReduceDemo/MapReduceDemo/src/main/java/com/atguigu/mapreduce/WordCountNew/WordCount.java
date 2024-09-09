package com.atguigu.mapreduce.WordCountNew;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author name 婉然从物
 * @create 2023-12-06 10:33
 */
public class WordCount {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        String user = "wanxiangguo";
        Job job = Job.getInstance(conf);
        job.setJobName("WordCount");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(doMapper.class);
        job.setReducerClass(doReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        Path in = new Path("hdfs://hadoop102:8020/mymapreduce1/in/README.txt");
        Path out = new Path("hdfs://hadoop102:8020/mymapreduce1/out");
        FileInputFormat.addInputPath(job, in);
        FileOutputFormat.setOutputPath(job, out);

        System.exit(job.waitForCompletion(true)?0 : 1);
    }

//    public class doMapper extends Mapper<Object, Text, Text, IntWritable> {
//        public final IntWritable one = new IntWritable(1);
//
//        public Text word = new Text();
//
//        @Override
//        protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
//            //super.map(key, value, context);
//            StringTokenizer tokenizer = new StringTokenizer(value.toString(), " ");
//
//            word.set(tokenizer.nextToken());
//
//            context.write(word, one);
//        }
//    }
//
//    public class doReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
//        private IntWritable result = new IntWritable();
//
//        @Override
//        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
//            int sum = 0;
//            for (IntWritable value : values) {
//                sum += value.get();
//            }
//
//            result.set(sum);
//            context.write(key, result);
//        }
//    }



}
