package io.chatasticlogin.kafka.config;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import lacrimae.chatasticlogin.kafka.proto.UserMessageOuterClass.UserMessage;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

@Configuration
public class KafkaProducerUserConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${topic.spring.kafka.schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(producerConfigs());
    }

    @Bean
    public ProducerFactory<String, UserMessage> userMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(),
                new StringSerializer(), new KafkaProtobufSerializer<>());
    }


    @Bean
    public KafkaTemplate<String, UserMessage> userMessageKafkaTemplate() {
        return new KafkaTemplate<>(userMessageProducerFactory());
    }

    @Bean
    public NewTopic user() {
        return new NewTopic("users", 1, (short) 1);
    }

    private Map<String, Object> producerConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaProtobufDeserializer.class);
        configs.put(SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        return configs;
    }
}
