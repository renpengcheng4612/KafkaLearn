package cn.pc.sz.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;

@Configuration
@Slf4j
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
    public void listen4ATopic(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        System.out.println("==============topics = 4a, groupId = test1 消费的值为 :================" + value);
        System.out.println(record);
        //手动提交offset
        //   ack.acknowledge();
    }


    /**
    * 显示的指定消费哪些Topic和分区的消息，
    *  设置每个Topic以及分区初始化的偏移量 设置消费线程并发度      设置消息异常处理器
    */
    @KafkaListener(id = "webGroup", topicPartitions = {
            @TopicPartition(topic = "chinaclear3", partitions = {"0", "1"}),
            @TopicPartition(topic = "chinaclear6", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
    }, concurrency = "6", errorHandler = "myErrorHandler")
    public String listenChinaClear(String input) {
        log.info("input value: {}", input);
        return "successful";
    }
}
