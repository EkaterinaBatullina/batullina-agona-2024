package com.technokratos.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import com.technokratos.config.property.KafkaCommonProperties;
import com.technokratos.config.property.KafkaProducerProperties;
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
    public Map<String, Object> producerConfig() {
        Map<String, Object> producerConfig = new HashMap<>();

        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getValueSerializer());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());

        return producerConfig;
    }

    @Bean
    public ProducerFactory<Long, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<Long, Object> kafkaTemplate() {
        KafkaTemplate<Long, Object> template = new KafkaTemplate<>(producerFactory());

        /*
         * Конвертация Java-объектов в JSON выполняется автоматически.
         *
         * Producer может публиковать различные типы событий через единый
         * KafkaTemplate без создания отдельных шаблонов для каждого DTO.
         */
        template.setMessageConverter(new StringJsonMessageConverter());

        return template;
    }
}
