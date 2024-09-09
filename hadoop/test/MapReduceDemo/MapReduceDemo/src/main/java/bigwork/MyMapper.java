package bigwork;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author name 婉然从物
 * @create 2023-12-25 10:12
 */
public class MyMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text OutK = new Text();
    private Text OutV= new Text();


    private int i = 0;  // 定义变量用于统计读取数据进度到第几行

        @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
        // TODO 1. 获取一行
        String line = value.toString();

        i++;    // 用于统计读取数据进度到第几行

        // TODO 2. ETL
        boolean result = judge(line, context);  // 该方法用于清洗掉脏数据

        if (!result){   // 若清洗数据的方法返回结果是false， 则直接读取下一行， 过滤掉当前行
            return;
        }

        // TODO 3. 写出到reduce端
        context.write(OutK, OutV);

    }

    /**
     * 定义map清洗数据的方法
     * @param line  读取到的当前行数据
     * @param context
     * @return  false表示脏数据
     */
    private boolean judge(String line, Context context) {
        String[] words = line.split(",");

        // 空白行需要过滤
        if (line.isEmpty()){
            return false;
        }

        // 数据长度不是7， 需要过滤
        if (words.length != 7){
            return false;
        }

        // 第一行需要过滤
        if (i == 1){
            return false;
        }

        // 匹配一行开头为任意个数字的ID， 过滤掉不是以数字开头的行
        String regex = "\\d*";
        if (!words[0].matches(regex)){
            return false;
        }

        // 过滤掉第四列数据只是负号， 没有数据的列
        if (words[3].equals("-")){
            return false;
        }

        OutK.set(words[2]);     // 将第三列，即MOVIEID作为map端输出的key
        OutV.set(words[3] + "\t" + words[4]);   // 将RATING、CONTENT列作为map端输出的value, 中间以制表符连接

        return true;    // 当前数据没有被过滤， 执行接下来的操作
    }
}