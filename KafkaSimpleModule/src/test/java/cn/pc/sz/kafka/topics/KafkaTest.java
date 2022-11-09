package cn.pc.sz.kafka.topics;

import cn.pc.sz.enmu.KafkaPropertiesEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaTest {

    @Test
    public void testTopic() {
        Set<String> topics = listTopic(KafkaPropertiesEnum.BOOTSTRAP_SERVERS_CONFIG_VALUE_1.getValue());
        System.out.println(topics.toString());
        boolean topic = createTopic(KafkaPropertiesEnum.BOOTSTRAP_SERVERS_CONFIG_VALUE_1.getValue(), "4a", 3, (short) 3);
        boolean result = containTopic(KafkaPropertiesEnum.BOOTSTRAP_SERVERS_CONFIG_VALUE_1.getValue(), "4a");
        System.out.println("--------------创建是否成功-----------------" + result);
        System.out.println("--------------创建topics后-----------------");
        Set<String> newTopics = listTopic(KafkaPropertiesEnum.BOOTSTRAP_SERVERS_CONFIG_VALUE_1.getValue());
        System.out.println(newTopics.toString());
    }

    /**
     * 创建topic
     *
     * @param bootstrapServers kafka集群地址  12.12.12.12:9092;12.12.12.10:9092;12.12.12.11:9092
     * @param topicName        topic的名称
     * @param partitions       分区数
     * @param replication      副本数
     */
    public static boolean createTopic(String bootstrapServers, String topicName, int partitions, short replication) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        AdminClient adminClient = null;
        try {
            adminClient = KafkaAdminClient.create(properties);
            NewTopic newTopic = new NewTopic(topicName, partitions, replication);
            CreateTopicsResult createTopicsResult = adminClient.createTopics(Lists.newArrayList(newTopic));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (adminClient != null) {
                adminClient.close();
            }
        }
        return true;
    }

    public Set<String> listTopic(String bootstrapServers) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        AdminClient adminClient = null;
        try {
            adminClient = KafkaAdminClient.create(properties);
            ListTopicsResult result = adminClient.listTopics();
            KafkaFuture<Set<String>> names = result.names();
            return names.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (adminClient != null) {
                adminClient.close();
            }
        }
        return Sets.newHashSet();
    }


    public boolean deleteTopic(String bootstrapServers, String topicName) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        AdminClient adminClient = null;
        try {
            adminClient = KafkaAdminClient.create(properties);
            adminClient.deleteTopics(Arrays.asList(topicName));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (adminClient != null) {
                adminClient.close();
            }
        }
        return false;
    }

    /**
     * 查询topic是否存在
     *
     * @param bootstrapServers kafka集群地址 kafka集群地址
     * @param topicName        topic名称
     * @return boolean
     */
    public static boolean containTopic(String bootstrapServers, String topicName) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        AdminClient adminClient = null;
        try {
            adminClient = KafkaAdminClient.create(properties);
            DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Lists.newArrayList(topicName));
            if (describeTopicsResult.values().values().isEmpty()) {
                System.out.println("找不到描述信息");
            } else {
                for (KafkaFuture<TopicDescription> value : describeTopicsResult.values().values()) {
                    System.out.println(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (adminClient != null) {
                adminClient.close();
            }
        }
        return true;
    }
}
