package cn.pc.sz.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;

@Configuration
@Slf4j
public class KafkaConfig {


    @Bean
    public NewTopic topic2() {
        return new NewTopic("topic-chinaclear-2", 3, (short) 3);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic("topic-chinaclear-3", 3, (short) 3);
    }


    @Bean
    public ConsumerAwareErrorHandler consumerAwareErrorHandler() {
        return new ConsumerAwareErrorHandler() {
            @Override
            public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
                log.info("Consumer Error Handler receive" + data.toString());
            }
        };
    }

}
