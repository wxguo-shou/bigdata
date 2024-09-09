package com.atguigu.fuctions;

import com.atguigu.bean.WaterSensor;
import org.apache.flink.api.common.functions.FilterFunction;

public class FilterFunctionImpl implements FilterFunction<WaterSensor> {
    public FilterFunctionImpl(String id) {
        this.id = id;
    }

    public String id;
    @Override
    public boolean filter(WaterSensor value) throws Exception {
        return this.id.equals(value.getId());
    }
}
