package com.technokratos.config;

import com.technokratos.config.property.KafkaCommonProperties;
import com.technokratos.config.property.KafkaProducerProperties;
import com.technokratos.event.OrderCreatedEvent;
import com.technokratos.event.PaymentRefundEvent;
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
    public Map<String, Object> orderCreatedEventProducerConfigs() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getOrderCreatedEventValueSerializer());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());
        return producerConfig;
    }

    @Bean
    public Map<String, Object> paymentRefundEventProducerConfigs() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getKeySerializer());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getPaymentRefundEventValueSerializer());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getClientId());
        return producerConfig;
    }

    @Bean
    public ProducerFactory<Long, OrderCreatedEvent> producerOrderCreatedEventFactory() {
        return new DefaultKafkaProducerFactory<>(orderCreatedEventProducerConfigs());
    }

    @Bean
    public ProducerFactory<Long, PaymentRefundEvent> producerPaymentRefundEventFactory() {
        return new DefaultKafkaProducerFactory<>(paymentRefundEventProducerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, OrderCreatedEvent> orderCreatedEventKafkaTemplate() {
        KafkaTemplate<Long, OrderCreatedEvent> template = new KafkaTemplate<>(producerOrderCreatedEventFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public KafkaTemplate<Long, PaymentRefundEvent> paymentRefundEventKafkaTemplate() {
        KafkaTemplate<Long, PaymentRefundEvent> template = new KafkaTemplate<>(producerPaymentRefundEventFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}