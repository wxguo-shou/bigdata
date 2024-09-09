package com.atguigu.fuctions;

import com.atguigu.bean.WaterSensor;
import org.apache.flink.api.common.functions.MapFunction;

public class MapFunctionlmpl implements MapFunction<WaterSensor, String> {
    @Override
    public String map(WaterSensor value) throws Exception {
        return value.getId();
    }
}
