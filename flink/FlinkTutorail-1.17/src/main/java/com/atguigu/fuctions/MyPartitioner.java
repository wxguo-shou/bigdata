package com.atguigu.fuctions;

import org.apache.flink.api.common.functions.Partitioner;
import org.apache.kafka.common.Cluster;

public class MyPartitioner implements Partitioner<String> {


    @Override
    public int partition(String key, int numPartitions) {
        return Integer.parseInt(key) % numPartitions;
    }
}
