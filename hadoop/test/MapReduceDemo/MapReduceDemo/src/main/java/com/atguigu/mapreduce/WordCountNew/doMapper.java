package com.atguigu.mapreduce.WordCountNew;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author name 婉然从物
 * @create 2023-12-06 10:21
 */
public class doMapper extends Mapper<Object, Text, Text, IntWritable> {
    public static final IntWritable one = new IntWritable(1);

    public static Text word = new Text();

    @Override
    protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        //super.map(key, value, context);

//        if (value.toString() == null){
//            return;
//        }
        if (value.toString().trim().isEmpty()) {
            return;
        }

        StringTokenizer tokenizer = new StringTokenizer(value.toString(), " ");

        word.set(tokenizer.nextToken());

        context.write(word, one);
    }
}
