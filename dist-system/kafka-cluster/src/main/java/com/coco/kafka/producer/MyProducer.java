package com.coco.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ExecutionException;

/**
 * 创建生产者，发送消息
 */
public class MyProducer {

    private static final String TOPIC = "myTopic";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //使用自定义配置构造生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(ProducerConfig.kafkaProps());
        //--------------------------异步发送--------------------------
        // asyncWithoutCallable(producer);
        // asyncWithCallaback(producer); //带回调的

        //--------------------------同步发送--------------------------
        syncSend(producer);


        producer.close();
    }

    private static void syncSend(KafkaProducer<String, String> producer) throws ExecutionException, InterruptedException {
        for (int i = 2000; i < 3000; i++) {
            //，一条消息发送之后，会阻塞当前线程，直至返回 ack。
            producer.send(new ProducerRecord<>(TOPIC, "msg-key-" + i, "msg-value" + i)).get();
        }
    }

    private static void asyncWithCallaback(KafkaProducer<String, String> producer) {
        for (int i = 10000; i < 100000; i++) {
            producer.send(
                    new ProducerRecord<>(TOPIC, "msg-key-" + i, "msg-value" + i),
                    (recordMetadata, e) -> {
                        //回调实现，生产者收到ack后处理
                        if (e == null){
                            System.out.println("success -> " + recordMetadata.offset());
                        }else{
                            e.printStackTrace();
                        }
                    });
        }
    }

    /**
     * 封装消息到 ProducerRecord 并发送(不带回调的，不处理ack)
     */
    private static void asyncWithoutCallable(KafkaProducer<String, String> producer) {
        for (int i = 0; i < 1000; i++) {
            producer.send(
                    new ProducerRecord<>(TOPIC, "msg-key-"+i, "msg-value"+i));
        }
    }



}
