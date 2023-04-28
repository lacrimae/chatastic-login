package io.chatasticlogin.kafka;

import lacrimae.chatasticlogin.kafka.proto.UserMessageOuterClass.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaUserPublisher {

    @Autowired
    private KafkaTemplate<String, UserMessage> kafkaTemplate;

    public void publish(String topic, String key, UserMessage message) {
        kafkaTemplate.send(topic, key, message);
    }
}
