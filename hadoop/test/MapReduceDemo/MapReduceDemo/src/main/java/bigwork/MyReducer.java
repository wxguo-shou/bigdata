package bigwork;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author name 婉然从物
 * @create 2023-12-25 11:31
 */
    public class MyReducer extends Reducer<Text, Text, String, NullWritable> {


    private String OutK;

    private Double avgRound;

    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, String, NullWritable>.Context context) throws IOException, InterruptedException {

        Integer count = 0;  // 每个电影出现了多少次

        Double sum = 0.0;    // 电影总的评分

        Double avg = 0.0;    // 电影平均评分

        String contents = null;


        ArrayList<Double> rating = new ArrayList<>();   // 定义集合用来存储电影评分

        ArrayList<String> content = new ArrayList<>();  // 定义集合用来存储电影评论


        for (Text value : values) { //      遍历Text对象

            String[] lines = value.toString().split("\t");    // 将Text对象转化成字符串再进行切割

            // 切割后的数组， 第一个索引表示电影评分  第二个索引表示电影评论
            rating.add(Double.parseDouble(lines[0]));   // 将字符串类型的电影评分转化为Double类型

            String result = null;   // 定义变量， 用于承接去掉首末6个引号的电影评论lines[1]

            if (lines[1].length() > 6){     // 若电影评论加6个引号大于6， 则将首末引号过滤
                result = lines[1].substring(3, lines[1].length() - 3);  // 截取字符串， 去掉首末6个引号
            } else {
                result = lines[1];      // 若电影评论加6个引号小于6， 则不处理该评论
            }

            content.add(result);        // 将评论内容添加到content集合
        }


        for (Double rat : rating) {     // 遍历评分    rating集合
            sum+=rat;                   // 将遍历到的集合相加， 赋值给sum
            count++;                    // count用于计数，   该电影评分数目
        }

        int tmp = 0;    // 定义临时遍历tmp， 用于判断电影评论是否为第一行

        for (String cont : content) {   // 遍历电影评论集合content
            if(tmp == 0){               // 表示是该电影第一行评论
                contents = cont;        // 这个电影的第一条电影直接复制给字符串contents
                tmp = 1;                // 将tmp设置为1，  表示接下来循环中的电影评论都不是第一条
            } else {
                contents = contents + "++&++" + cont;   // 从第二条电影评论开始， 前面添加++&++，
                                                        // 并连接到字符串contents
            }
        }


        avg = sum / count;      // 计算电影评分平均值

        avgRound = Math.round(avg * 10.0) / 10.0  ;     // 将评分平均值四舍五入， 并保留一位小数

        // 将MOVIEID、平均评分、所有评论   作为reduce端输出的主键，
        OutK = key.toString() + "\t" + OutK + "\t" + contents;

        // TODO 写出
        context.write(OutK, null);
    }

}