package cn.pc.sz.config;


import cn.pc.sz.service.impl.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Supplier;

//@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(MessageServiceImpl.class)
@ConditionalOnBean({KafkaTemplate.class, MessageServiceImpl.class})
@AutoConfigureAfter({KafkaAutoConfiguration.class})
@EnableConfigurationProperties({MessageProperties.class})
@ConditionalOnProperty(name = "csdc.kafka.topic.enable", havingValue = "true", matchIfMissing = true)
@Slf4j
@Component
public class MessageTopicConfiguration {

    public MessageTopicConfiguration() {
        System.out.println("1");
    }


    @PostConstruct
    public void registerTopic(ApplicationContext applicationContext, MessageProperties messageProperties) {
        log.info("开始注册topic");
        if (applicationContext instanceof AnnotationConfigServletWebApplicationContext) {
            AnnotationConfigServletWebApplicationContext ctx = (AnnotationConfigServletWebApplicationContext) applicationContext;
            Map<String, MessageConfig> topicConfigMap = messageProperties.getTopicConfigMap();
            if (topicConfigMap != null) {
                topicConfigMap.forEach((k, v) -> {
                    NewTopic topic = new NewTopic(k, v.getTopicPartition(), v.getTopicReplication());
                    Supplier<NewTopic> topicSupplier = () -> topic;
                    ctx.registerBean(k, NewTopic.class, topicSupplier);
                    log.info("Topic bean {} 注册完成", k);
                });
            }
        }
    }
}
