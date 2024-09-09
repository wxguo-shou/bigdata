package com.atguigu.state;

import com.atguigu.bean.WaterSensor;
import com.atguigu.fuctions.WaterSensorMapFunction;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;

public class KeyedMapStateDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        SingleOutputStreamOperator<WaterSensor> sensorDS = env
                .socketTextStream("hadoop102", 7777)
                .map(new WaterSensorMapFunction())
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy
                                .<WaterSensor>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                                .withTimestampAssigner((element, ts) -> element.getTs() * 1000L)
                );

        sensorDS.keyBy(r -> r.getId())
                        .process(
                                new KeyedProcessFunction<String, WaterSensor, String>() {
                                    MapState<Integer, Integer> vcCountMapState;

                                    @Override
                                    public void open(Configuration parameters) throws Exception {
                                        super.open(parameters);
                                        vcCountMapState = getRuntimeContext().getMapState(new MapStateDescriptor<>("vcCountMapState", Types.INT, Types.INT));
                                    }

                                    @Override
                                    public void processElement(WaterSensor value, KeyedProcessFunction<String, WaterSensor, String>.Context ctx, Collector<String> out) throws Exception {
                                        // 1. 判断是否存在vc对应的key
                                        Integer vc = value.getVc();
                                        if(vcCountMapState.contains(vc)){
                                            // 1.1 如果包含这个vc的key， 直接对value + 1
                                            Integer count = vcCountMapState.get(vc);
                                            vcCountMapState.put(vc, ++count);
                                        }else {
                                            // 1.2 如果不包含这个vc的key， 初始化put进去
                                            vcCountMapState.put(vc, 1);
                                        }

                                        // 遍历Map状态， 输出每一个k-v的值
                                        StringBuilder outStr = new StringBuilder();
                                        outStr.append("=================================\n");
                                        outStr.append("传感器id为" + value.getId() + "\n");
                                        for (Map.Entry<Integer, Integer> vcCount : vcCountMapState.entries()) {
                                            outStr.append(vcCount.toString() + "\n");
                                        }
                                        outStr.append("=================================\n");
                                        out.collect(outStr.toString());

//                                        vcCountMapState.get();          // 对本组的Map状态，根据key，获取vaLue
//                                        vcCountMapState.contains();     // 对本组的Map状态，判断key是否存在
//                                        vcCountMapState.put();          // 对本组的Map状态，添加一个 键值对
//                                        vcCountMapState.putAll();       // 对本组的Map状态，添加多个 键值对
//                                        vcCountMapState.entries();      // 对本组的Map状态，获取所有键值对
//                                        vcCountMapState.keys();         // 对本组的Map状态，获取所有键
//                                        vcCountMapState.values();       // 对本组的Map状态，获取所有值
//                                        vcCountMapState.remove( );      // 对本组的Map状态，移除键值对
//                                        vcCountMapState.isEmpty();      // 对本组的Map状态，根据指定key，判断是否为空
//                                        vcCountMapState.iterator();     // 对本组的Map状态，获取送代器
//                                        vcCountMapState.clear();        // 对本组的Map状态，清空


                                    }
                                }
                        )
                                .print();
        env.execute();
    }
}

