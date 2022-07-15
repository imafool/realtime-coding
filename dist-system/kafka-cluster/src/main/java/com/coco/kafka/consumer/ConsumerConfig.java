package com.coco.kafka.consumer;

import java.util.Properties;

/**
 * 消费者配置
 */
public class ConsumerConfig {
    public static Properties kafkaProps(){
        Properties prop = new Properties();
        //kafka cluster
        prop.put("bootstrap.servers", "hadoop101:9092");

        //TODO 消费者组
        prop.put("group.id", "demoGroup");

        prop.put("enable.auto.commit", "true"); //是否开启自动提交，手动提交改为false，相应消费数据方法
        prop.put("auto.commit.intervals.ms", "1000"); //自动提交时间间隔

        //消息数据的序列化配置
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return prop;
    }
}
