package cn.pc.sz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "csdc.kafka.topic")
public class MessageProperties {

    Map<String,MessageConfig> topicConfigMap;

}
