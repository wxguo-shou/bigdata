package com.atguigu.mapreduce.partitioner2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class ProvincePartitioner extends Partitioner<Text, FlowBean> {
    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        String phone = text.toString();
        String prePhone = phone.substring(0, 3);

        switch (prePhone){
            case  "136":
                return 0;

            case "137":
                return 1;

            case  "138":
                return 2;

            case "139":
                return 3;

            default:
                return 4;
        }
    }
}
