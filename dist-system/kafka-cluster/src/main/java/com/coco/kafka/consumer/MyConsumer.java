package com.coco.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class MyConsumer {
    private static final String TOPIC = "myTopic";

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(ConsumerConfig.kafkaProps());

        //订阅topic
        consumer.subscribe(Collections.singletonList(TOPIC));

        //自动提交 offset
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(100);//如果没有可供消费数据，100ms后返回
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }

            // 手动提交 offset 的方法有两种：分别是 commitSync（同步提交）和 commitAsync（异步提交）。两者的相同点是，都会将本次 poll 的一批数据最高的偏移量提交

            // consumer.commitSync(); // 修改配置。同步提交，有重试机制，更加可靠，当前线程会阻塞直到 offset 提交成功，因此吞吐量会收到很大的影响

            // consumer.commitAsync((offsetAndMetadata, e) -> {  //异步提交
            //     if (e != null) {
            //         System.err.println("Commit failed for" + offsetAndMetadata);
            //     }
            // });

            /**
             * 同步 or 异步提交offset，都可能会造成数据的漏消费和重复消费
             * 先提交offset，后消费 -- 漏消费
             * 先消费，后提交offset -- 数据重复消费
             */
        }


        //------------------自定义存储offset
        //需要考虑消费者的 rebalance，机制如下：
        /**
         * 当有新的消费者加入消费者组、已有的消费者推出消费者组或者所订阅的主题的分区发生变化，就会触发到分区的重新分配，重新分配的过程叫做 Rebalance。
         * 消费者发生 Rebalance 之后，每个消费者消费的分区就会发生变化。因此消费者要首先获取到自己被重新分配到的分区，并且定位到每个分区最近提交的 offset 位置继续消费。
         * 要实现自定义存储 offset，需要借助 ConsumerRebalanceListener
         */

        // //消费者订阅主题
        // consumer.subscribe(Arrays.asList("first"), new ConsumerRebalanceListener() {
        //
        //             //该方法会在 Rebalance 之前调用
        //             @Override
        //             public void
        //             onPartitionsRevoked(Collection<TopicPartition> partitions) {
        //                 commitOffset(currentOffset);
        //             }
        //             //该方法会在 Rebalance 之后调用
        //             @Override
        //             public void
        //             onPartitionsAssigned(Collection<TopicPartition> partitions) {
        //                 currentOffset.clear();
        //                 for (TopicPartition partition : partitions) {
        //                     consumer.seek(partition, getOffset(partition));//定位到最近提交的 offset 位置继续消费
        //                 }
        //             }
        //         });
        // while (true) {
        //     ConsumerRecords<String, String> records =
        //             consumer.poll(100);//消费者拉取数据
        //     for (ConsumerRecord<String, String> record : records) {
        //         System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
        //         currentOffset.put(new TopicPartition(record.topic(),
        //                 record.partition()), record.offset());
        //     }
        //     commitOffset(currentOffset);//异步提交
        // }
    }

    // //获取某分区的最新 offset
    // private static long getOffset(TopicPartition partition) {
    //     return 0;
    // }
    // //提交该消费者所有分区的 offset
    // private static void commitOffset(Map<TopicPartition, Long> currentOffset) {
    // }
}
