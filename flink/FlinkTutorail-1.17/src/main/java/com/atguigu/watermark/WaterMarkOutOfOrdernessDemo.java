package com.atguigu.watermark;

import com.atguigu.bean.WaterSensor;
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

public class WaterMarkOutOfOrdernessDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        /**
         * 演示watermark多并行度下的传递
         * 1、接收到上游多个，取最小
         * 2、往下游多个发送，广播
         */
        env.setParallelism(2);

        // env.getConfig().setAutoWatermarkInterval();  // 设置 watermark 刷新 默认时间

        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("hadoop102", 7777)
                .map(new WaterSensorMapFunction());

        // TODO 1.定义 watermark策略
        // 1、定义watermark

        WatermarkStrategy<WaterSensor> watermarkStrategy = WatermarkStrategy
                // 1.1 指定watermark生成： 乱序的， 等待3s
                .<WaterSensor>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                // 指定 时间戳分配器， 从数据中提取
                .withTimestampAssigner(
                        (element, recordTimestamp) -> {
                                // 返回的时间戳， 要 毫秒
                                System.out.println("数据=" + element + ", recordTs=" + recordTimestamp);
                                return element.getTs() * 1000L;
                            }

                );

        // TODO 2.指定 watermark策略
        SingleOutputStreamOperator<WaterSensor> sensorDSwithWatermark = sensorDS.assignTimestampsAndWatermarks(watermarkStrategy);

        sensorDSwithWatermark.keyBy(r -> r.getId())
                // 指定 使用 事件时间语义窗口
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .process(
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
        )
        .print();

        env.execute();
    }
}

/**
 * TODO 内置watermark的生成原理
 * 都是周期性生成的: 默认200ms
 * 有序流:watermark = 当前最大的事件时间 - 1ms
 * 3、乱序流:watermark = 当前最大的事件时间 - 延迟时间 - 1ms
 */