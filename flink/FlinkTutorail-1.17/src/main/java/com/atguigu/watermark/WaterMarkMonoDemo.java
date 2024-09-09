package com.atguigu.watermark;

import com.atguigu.bean.WaterSensor;
import com.atguigu.fuctions.WaterSensorMapFunction;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

public class WaterMarkMonoDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("hadoop102", 7777)
                .map(new WaterSensorMapFunction());

        // TODO 指定watermark策略
        // 1、定义watermark

        WatermarkStrategy<WaterSensor> watermarkStrategy = WatermarkStrategy
                // 升序的watermark, 没有等待时间
                .<WaterSensor>forMonotonousTimestamps()
                // 指定 时间戳分配器， 从数据中提取
                .withTimestampAssigner(
//                        new SerializableTimestampAssigner<WaterSensor>() {
//                            @Override
//                            public long extractTimestamp(WaterSensor element, long recordTimestamp) {
//                                // 返回的时间戳， 要 毫秒
//                                System.out.println("数据=" + element + ", recordTs=" + recordTimestamp);
//                                return element.getTs() * 1000L;
//                            }
//                        }

                        (WaterSensor element, long recordTimestamp) -> {
                                // 返回的时间戳， 要 毫秒
                                System.out.println("数据=" + element + ", recordTs=" + recordTimestamp);
                                return element.getTs() * 1000L;
                            }

                );

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
