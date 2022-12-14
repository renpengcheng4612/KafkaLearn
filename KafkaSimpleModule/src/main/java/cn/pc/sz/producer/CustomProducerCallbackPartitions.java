package cn.pc.sz.producer;

import cn.pc.sz.enmu.KafkaPropertiesEnum;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class CustomProducerCallbackPartitions {

    public static void main(String[] args) throws InterruptedException {
        // 0 配置
        Properties properties = new Properties();
        // 连接集群 bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaPropertiesEnum.BOOTSTRAP_SERVERS_CONFIG_VALUE_1.getValue());
        // 指定对应的key和value的序列化类型 key.serializer
        //properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 关联自定义分区器
        //  properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "cn.pc.sz.producer.MyPartitioner");
        // 1 创建kafka生产者对象
        // "" hello
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        // 2 发送数据
        for (int i = 0; i < 500; i++) {
            kafkaProducer.send(new ProducerRecord<>("4a", "hello" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("主题：" + metadata.topic() + " 分区： " + metadata.partition());
                    }
                }
            });
            Thread.sleep(2);
        }
        // 3 关闭资源
        kafkaProducer.close();
    }
}
