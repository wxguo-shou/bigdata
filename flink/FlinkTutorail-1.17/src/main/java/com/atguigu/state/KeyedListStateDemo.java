package com.atguigu.state;

import com.atguigu.bean.WaterSensor;
import com.atguigu.fuctions.WaterSensorMapFunction;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.ArrayList;

public class KeyedListStateDemo {
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

                                    ListState<Integer> vcListState;

                                    @Override
                                    public void open(Configuration parameters) throws Exception {
                                        super.open(parameters);
                                        vcListState = getRuntimeContext().getListState(new ListStateDescriptor<Integer>("vcListState", Types.INT));
                                    }

                                    @Override
                                    public void processElement(WaterSensor value, KeyedProcessFunction<String, WaterSensor, String>.Context ctx, Collector<String> out) throws Exception {
                                        // 1. 来一条， 存储到List状态里
                                        vcListState.add(value.getVc());

                                        // 2. 从List状态里拿出来(Interable)， 拷贝到一个List中， 排序， 只留三个最大的
                                        Iterable<Integer> vcListIt = vcListState.get();
                                        // 2.1 拷贝到List中
                                        ArrayList<Integer> vcList = new ArrayList<>();
                                        for (Integer vc : vcListIt) {
                                            vcList.add(vc);
                                        }
                                        // 2.2 对List进行 降序排序
                                        vcList.sort((o1, o2) -> o2 - o1);
                                        // 2.3 只保留最大的三个
                                        if(vcList.size() > 3){
                                            // 将索引为3 的清除（第四个元素）
                                            vcList.remove(3);
                                        }

                                        out.collect("传感器为"+value.getId()+"，最大的三个水位值="+vcList.toString());

                                        // 3. 更新List状态
                                        vcListState.update(vcList);

//                                         ListState.get();   // 取出 List状态 本组的数据，是一个Iteroblevc
//                                         vcListState.add();           // 向 list状态 本组 添加一个元卖
//                                         vcListState.addAll();         // 向 List状态 本组 添加多个元素
//                                         vcListState.update();        // 更新 list状态本组数据(覆盖)
//                                         vcListState.clear();         // 清空List状态 本组数据

                                    }
                                }
                        )
                                .print();
        env.execute();
    }
}

