package com.technokratos.config;

import com.technokratos.config.property.KafkaCommonProperties;
import com.technokratos.config.property.KafkaProducerProperties;
import com.technokratos.event.InventoryFailedEvent;
import com.technokratos.event.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
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
    public Map<String, Object> inventoryReservedEventProducerConfigs() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getInventoryReservedEventValueSerializer());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());
        return producerConfig;
    }

    @Bean
    public Map<String, Object> inventoryFailedEventProducerConfigs() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getInventoryFailedEventValueSerializer());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());
        return producerConfig;
    }

    @Bean
    public ProducerFactory<Long, InventoryReservedEvent> producerInventoryReservedEventFactory() {
        return new DefaultKafkaProducerFactory<>(inventoryReservedEventProducerConfigs());
    }

    @Bean
    public ProducerFactory<Long, InventoryFailedEvent> producerInventoryFailedEventFactory() {
        return new DefaultKafkaProducerFactory<>(inventoryFailedEventProducerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, InventoryReservedEvent> producerInventoryReservedEventKafkaTemplate() {
        KafkaTemplate<Long, InventoryReservedEvent> template = new KafkaTemplate<>(producerInventoryReservedEventFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public KafkaTemplate<Long, InventoryFailedEvent> inventoryFailedEventKafkaTemplate() {
        KafkaTemplate<Long, InventoryFailedEvent> template = new KafkaTemplate<>(producerInventoryFailedEventFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}