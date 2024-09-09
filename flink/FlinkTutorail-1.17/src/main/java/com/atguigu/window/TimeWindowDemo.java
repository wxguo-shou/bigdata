package com.atguigu.window;

import com.atguigu.bean.WaterSensor;
import com.atguigu.fuctions.WaterSensorMapFunction;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.SessionWindowTimeGapExtractor;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class TimeWindowDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SingleOutputStreamOperator<WaterSensor> sensorDS = env.socketTextStream("hadoop102", 7777)
                .map(new WaterSensorMapFunction());

        KeyedStream<WaterSensor, String> sensorKS = sensorDS.keyBy(r -> r.getId());

        // 1、 窗口分配器
        WindowedStream<WaterSensor, String, TimeWindow> sensorWS = sensorKS
                .window(TumblingProcessingTimeWindows.of(Time.seconds(10)));  // 滚动窗口， 窗口长度10s
//                .window(SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(5)));  // 滑动窗口， 窗口长度10s， 步长5s
//                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(5))); // 会话窗口， 间隔5s
//                .window(ProcessingTimeSessionWindows.withDynamicGap(r -> r.getTs() * 1000));  // 会话窗口， 动态间隔， 每条来的数据都会更新 间隔时间

        SingleOutputStreamOperator<String> process = sensorWS.process(
                new ProcessWindowFunction<WaterSensor, String, String, TimeWindow>() {
                    @Override
                    public void process(String s, ProcessWindowFunction<WaterSensor, String, String, TimeWindow>.Context context, Iterable<WaterSensor> iterable, Collector<String> collector) throws Exception {
                        // 上下文可以拿到window对象， 还有其他东西： 侧输出流 等等
                        long startTs = context.window().getStart();
                        long endTs = context.window().getEnd();
                        String windowStart = DateFormatUtils.format(startTs, "yyyy-MM-dd HH:mm:ss.SSS");
                        String windowEnd = DateFormatUtils.format(endTs, "yyyy-MM-dd HH:mm:ss.SSS");

                        long count = iterable.spliterator().estimateSize();

                        collector.collect("key=" + s + "的窗口[" + windowStart + "," + windowEnd + ")，包含" + count
                                + "条数据===>" + iterable.toString());
                    }
                }
        );

        process.print();

        env.execute();
    }
}

/**
 * 触发器、移除器: 现成的几个窗口，都有默认的实现，一般不需要自定义
 *
 * 以 时间类型的 滚动窗口 为例，分析原理:
 * TODO 1、窗口什么时候触发 输出?
 *      时间进展>= 窗口的最大时间戳 (end - 1ms )
 *
 * TODO 2、窗口是怎么划分的?
 *      start= 向下取整，取窗口长度的整数倍
 *      end = start + 窗口长度
 *
 *      窗口左闭右开 ==》 属于本窗口的 最大时间戳=end - 1ms
 * TODO 3、窗口的生命周期?
 *      创建: 属于本窗口的第一条数据来的时候，现new的，放入一个singleton单例的集合中
 *      销毁(关窗): 时间进展 >= 窗口的最大时间戳 (end - 1ms) + 许迟到的时间(默认)
 *
 * long start = TimeWindow.getWindowStartWithOffset(now,
 * (this.globalOffset + this.staggerOffset) % this.size, this.size);
 */