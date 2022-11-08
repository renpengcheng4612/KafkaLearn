package cn.pc.sz;

import com.google.common.collect.Sets;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;
import org.junit.Test;


import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaTest {


    @Test
    public void testTopic() {


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


}
