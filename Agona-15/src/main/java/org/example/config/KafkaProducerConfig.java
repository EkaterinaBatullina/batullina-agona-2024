package org.example.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.example.config.property.KafkaCommonProperties;
import org.example.config.property.KafkaProducerProperties;
import org.example.dto.StarshipDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaCommonProperties commonProperties;
    private final KafkaProducerProperties kafkaProperties;

    @Bean
    public Map<String, Object> stringProducerConfigs() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getValueSerializer());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());
        return producerConfig;
    }

    @Bean
    public Map<String, Object> starshipDtoProducerConfigs() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getStarshipValueSerializer());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());
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