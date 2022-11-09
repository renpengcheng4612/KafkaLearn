package cn.pc.sz.service.impl;

import cn.pc.sz.service.MessageService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private final KafkaTemplate<String, String> messageTemplate;

    private final Gson gson = new Gson();

    @Autowired
    public MessageServiceImpl(KafkaTemplate<String, String> messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    @Override
    public <T> void sendMessage(String topic, T data) {
        messageTemplate.send(topic, gson.toJson(data)).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                assert ex != null;
                log.error("发送消息失败:{}", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                assert result != null;
                log.info("发送消息成功：{}", result.getRecordMetadata().toString());
            }
        });
    }
}
