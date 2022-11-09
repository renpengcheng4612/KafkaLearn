package cn.pc.sz.config;

import lombok.Data;

@Data
public class MessageConfig {

    private int topicPartition = 30;

    private short topicReplication = 3;
}
