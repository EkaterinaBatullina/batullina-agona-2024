package org.example.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.dto.StarshipDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.producer.client-id}")
    private String kafkaProducerId;

    @Bean
    public Map<String, Object> stringProducerConfigs() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return producerConfig;
    }

    @Bean
    public Map<String, Object> starshipDtoProducerConfigs() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return producerConfig;
    }

    @Bean
    public ProducerFactory<Long, StarshipDto> producerStarshipFactory() {
        return new DefaultKafkaProducerFactory<>(starshipDtoProducerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, StarshipDto> starshipDtoKafkaTemplate() {
        KafkaTemplate<Long, StarshipDto> template = new KafkaTemplate<>(producerStarshipFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public KafkaTemplate<Long, String> stringKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(stringProducerConfigs()));
    }
}