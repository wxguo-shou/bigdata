package com.atguigu.watermark;

import com.atguigu.bean.WaterSensor;
import com.atguigu.fuctions.WaterSensorMapFunction;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

public class WaterMarkCustomDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        env.getConfig().setAutoWatermarkInterval(2000);

        // env.getConfig().setAutoWatermarkInterval();  // 设置 watermark 刷新 默认时间

        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("hadoop102", 7777)
                .map(new WaterSensorMapFunction());

        WatermarkStrategy<WaterSensor> watermarkStrategy = WatermarkStrategy
                // TODO 指定 自定义的 生成器
//                .<WaterSensor>forGenerator(new WatermarkStrategy<WaterSensor>() {
//                    @Override
//                    public WatermarkGenerator<WaterSensor> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
//                        return new MyPeriodWatermarkGenerator<>(3);
//                    }
//                })

                // 1.自定义的 周期性生成
//                .<WaterSensor>forGenerator(ctx -> new MyPeriodWatermarkGenerator<>(3))
                // 2.自定义的 断点式 生成
                .<WaterSensor>forGenerator(ctx -> new MyPuntuatedWatermarkGenerator<>(3))

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
