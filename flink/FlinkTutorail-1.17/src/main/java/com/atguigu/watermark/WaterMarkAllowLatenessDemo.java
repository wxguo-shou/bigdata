package com.atguigu.watermark;

import com.atguigu.bean.WaterSensor;
import com.atguigu.fuctions.WaterSensorMapFunction;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Duration;

public class WaterMarkAllowLatenessDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("hadoop102", 7777)
                .map(new WaterSensorMapFunction());

        WatermarkStrategy<WaterSensor> watermarkStrategy = WatermarkStrategy
                // 1.1 指定watermark生成： 乱序的， 等待3s
                .<WaterSensor>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                // 指定 时间戳分配器， 从数据中提取
                .withTimestampAssigner(
                        (element, recordTimestamp) -> {
                                // 返回的时间戳， 要 毫秒
                                return element.getTs() * 1000L;
                            }

                );

        SingleOutputStreamOperator<WaterSensor> sensorDSwithWatermark = sensorDS.assignTimestampsAndWatermarks(watermarkStrategy);

        OutputTag<WaterSensor> lateTag = new OutputTag<>("late-data", Types.POJO(WaterSensor.class));
        SingleOutputStreamOperator<String> process = sensorDSwithWatermark.keyBy(r -> r.getId())
                // 指定 使用 事件时间语义窗口
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .allowedLateness(Time.seconds(2))   // 推迟2s关窗
                .sideOutputLateData(lateTag)  // 关窗后的迟到数据， 放入侧输出流
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
                );

        process.print();

        // 从主流获取 侧输出流， 打印
        process.getSideOutput(lateTag).printToErr("关窗后的迟到数据");

        env.execute();
    }
}

/**
 * 1、乱序与迟到的区别
 * 乱序:数据的顺序乱了，时间小的比时间大的 晚来
 * 迟到: 数据的时间戳 < 当前的watermark
 * 2、乱序、迟到数据的处理
 * 1） watermark中指定 乱序等待时间
 * 2） 如果开窗，设置窗口允许迟到
 *      =》 推迟关窗时间，在关窗之前，迟到数据来了，还能被窗口计算，来一条迟到数据触发一次计算
 *      =》 关窗后，迟到数据不会被计算
 * 3） 关窗后的迟到数据，放入侧输出流
 *
 * 如果 watermark等待3s，窗口允许迟到2s，为什么不直接 watermark 等待5s 或者 窗口允许迟到5s ?
 *  =》 watermark 等待时间不会设太大 ===》 影响的计算延迟
 *      如果3s ==》 窗口第一次触发计算和输出， 13s的数据来。 13-3=10s
 *      如果5s ==》 窗口第一次触发计算和输出， 15s的数据来。 15-5=10s
*   =》 窗口允许迟到，是对 大部分迟到数据的 处理，尽量让结果准确
 *      如果只设置允许迟到5s，那么 就会导致 频繁 重新输出
 * TODO 设置经验
 * 1、watermark等待时间，设置一个不算特别大的，一般是秒级，在 乱序和 延迟 取舍
 * 2、设置一定的窗口允许迟到，只考虑大部分的迟到数据，极端小部分迟到很久的数据，不管
 * 3、极端小部分迟到很久的数据， 放到侧输出流。 获取到之后可以做各种处理
 */