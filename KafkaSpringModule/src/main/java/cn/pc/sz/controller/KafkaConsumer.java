package cn.pc.sz.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Configuration
public class KafkaConsumer {

    //如果bean的方法中有使用注解@KafkaListener，
    // 则会在KafkaListenerAnnotationBeanPostProcessor#postProcessAfterInitialization 方法中做相应的处理
    @KafkaListener(topics = "chinaclear", groupId = "test")
    public void consumerTopic(String msg) {
        System.out.println("收到消息：" + msg);
    }

    /**
     * @param record
     * @KafkaListener(groupId = "testGroup", topicPartitions = {
     * @TopicPartition(topic = "topic1", partitions = {"0", "1"}),
     * @TopicPartition(topic = "topic2", partitions = "0",
     * partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
     * },concurrency = "6")
     * concurrency就是同组下的消费者个数，就是并发消费数，必须小于等于分区总数
     */
    @KafkaListener(topics = "4a", groupId = "test1")
    public void listen4AGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println("==============topics = 4a, groupId = test1 消费的值为 :================"+value);
        System.out.println(record);
        //手动提交offset
     //   ack.acknowledge();
    }
}
