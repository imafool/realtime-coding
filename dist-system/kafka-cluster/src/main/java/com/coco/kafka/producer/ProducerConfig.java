package com.coco.kafka.producer;

import java.util.Properties;

/**
 * 生产者一些列配置
 */
public class ProducerConfig {
    public static Properties kafkaProps(){
        Properties prop = new Properties();
        //kafka cluster
        prop.put("bootstrap.servers", "hadoop101:9092");
        //acks 策略：至少一次，保证消费不丢失，可能会重复
        prop.put("acks", "all");
        //重发次数，未收到broker的ack
        prop.put("reties", 1);
        //消费者记录收集器内部参数
        prop.put("batch.size", 16384);
        prop.put("linger.ms", 1);
        prop.put("buffer.memory", 33554432);
        //消息数据的序列化配置
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return prop;
    }
}
