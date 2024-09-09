package com.atguigu.process;

import com.atguigu.bean.WaterSensor;
import com.atguigu.fuctions.WaterSensorMapFunction;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.*;

public class ProcessAllWindowTopNDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("hadoop102", 7777)
                .map(new WaterSensorMapFunction())
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy
                                .<WaterSensor>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                                .withTimestampAssigner(
                                        (element, recordTimestamp) -> {
                                            return element.getTs() * 1000L;
                                        }
                                )
                );

        // 最近10s = 窗口长度，  每5s输出 = 滑动步长
        // TODO 思路一： 所有数据到一起， 用hashMap储存， key=vc, value=count 值
        sensorDS.windowAll(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(5)))
                .process(new MyTopNPAWF())
                .print();

        env.execute();
    }

    public static class MyTopNPAWF extends ProcessAllWindowFunction<WaterSensor, String, TimeWindow>{

        @Override
        public void process(ProcessAllWindowFunction<WaterSensor, String, TimeWindow>.Context context, Iterable<WaterSensor> iterable, Collector<String> collector) throws Exception {
            // 定义一个hashmap用来存， key=vc, value=count值
            Map<Integer, Integer> vcCountMap = new HashMap<>();
            // 1.遍历数据， 统计 各个vc 出现的次数
            for (WaterSensor element : iterable) {
                Integer vc = element.getVc();
                if (vcCountMap.containsKey(vc)) {
                    // 1.1 key存在， 不是key的第一条数据， 直接累加
                    vcCountMap.put(vc, vcCountMap.get(vc) + 1);

                }else {
                    // 1.2 key不存在， 初始化
                    vcCountMap.put(vc, 1);
                }
            }

            // 2.对 count值 进行排序: 利用List 来实现排序
            List<Tuple2<Integer, Integer>> datas = new ArrayList<>();
            for (Integer vc : vcCountMap.keySet()) {
                datas.add(Tuple2.of(vc, vcCountMap.get(vc)));
            }
            // 对List进行排序， 根据count值， 降序
            datas.sort(new Comparator<Tuple2<Integer, Integer>>() {
                @Override
                public int compare(Tuple2<Integer, Integer> o1, Tuple2<Integer, Integer> o2) {
                    // 降序 后 - 前
                    return o2.f1 - o1.f1;
                }
            });

            // 取出 count最大的2个vc
            StringBuilder outStr = new StringBuilder();

            outStr.append("======================");
            // 遍历 排序后的 List，取出前2个，考虑可能List不够2个的情况 ==》 List中元素的个数 和 2 取最小值
            for (int i = 0; i < Math.min(2, datas.size()); i++) {
                Tuple2<Integer, Integer> vcCount = datas.get(i);
                outStr.append("Top" + (i + 1));
                outStr.append("\n");
                outStr.append("vc=" + vcCount.f0);
                outStr.append("\n");
                outStr.append("count=" + vcCount.f1);
                outStr.append("\n");
                outStr.append("窗口的结束时间=" + DateFormatUtils.format(context.window().getEnd(), "yy-MM-dd HH:mm:ss.SSS"));
                outStr.append("\n");
                outStr.append("=====================");
            }

            collector.collect(outStr.toString());

        }
    }

}


