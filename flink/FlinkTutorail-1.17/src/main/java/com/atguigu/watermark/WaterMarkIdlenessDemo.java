package com.atguigu.watermark;

import com.atguigu.bean.WaterSensor;
import com.atguigu.fuctions.MyPartitioner;
import com.atguigu.fuctions.WaterSensorMapFunction;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

public class WaterMarkIdlenessDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        // 自定义分区器: 数据%分区数，只输入奇数，都只会去往map 的一个子任务
        SingleOutputStreamOperator<Integer> socketDS = env
                .socketTextStream("hadoop102", 7777)
                .partitionCustom(new MyPartitioner(), r -> r)
                .map(r -> Integer.parseInt(r))
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy
                                .<Integer>forMonotonousTimestamps()
                                .withTimestampAssigner((r, ts) -> r * 1000L)
                                .withIdleness(Duration.ofSeconds(10))
                );

        // 分成两组: 奇数一组，偶数一组 ， 开10s的事件时间滚动窗口
        socketDS.keyBy(r -> r % 2)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .process(new ProcessWindowFunction<Integer, String, Integer, TimeWindow>() {
                    @Override
                    public void process(Integer integer, ProcessWindowFunction<Integer, String, Integer, TimeWindow>.Context context, Iterable<Integer> iterable, Collector<String> collector) throws Exception {
                        long startTs = context.window().getStart();
                        long endTs = context.window().getEnd();
                        String windowStart = DateFormatUtils.format(startTs, "yyyy-MM-dd HH:mm:ss.SSS");
                        String windowEnd = DateFormatUtils.format(endTs, "yyyy-MM-dd HH:mm:ss.SSS");

                        long count = iterable.spliterator().estimateSize();

                        collector.collect("key=" + integer + "的窗口[" + windowStart + "," + windowEnd + ")，包含" + count
                                + "条数据===>" + iterable.toString());
                    }
                })
                .print();

        env.execute();
    }
}
