package com.technokratos.config;

import com.technokratos.config.property.KafkaCommonProperties;
import com.technokratos.config.property.KafkaConsumerProperties;
import com.technokratos.event.InventoryFailedEvent;
import com.technokratos.event.InventoryReservedEvent;
import com.technokratos.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaCommonProperties commonProperties;
    private final KafkaConsumerProperties kafkaProperties;

    @Bean
    public Map<String, Object> paymentFailedEventConsumerConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getPaymentFailedEventValueDeserializer());
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaProperties.getTrustedPackages());
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, kafkaProperties.getPaymentFailedEventDefaultType());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.isEnableAutoCommit());
        return consumerConfig;
    }

    @Bean
    public Map<String, Object> inventoryReservedEventConsumerConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getInventoryReservedEventValueDeserializer());
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaProperties.getTrustedPackages());
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, kafkaProperties.getInventoryReservedEventDefaultType());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.isEnableAutoCommit());
        return consumerConfig;
    }

    @Bean
    public Map<String, Object> inventoryFailedEventConsumerConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getInventoryFailedEventValueDeserializer());
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaProperties.getTrustedPackages());
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, kafkaProperties.getInventoryFailedEventDefaultType());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.isEnableAutoCommit());
        return consumerConfig;
    }

    @Bean
    public ConsumerFactory<Long, PaymentFailedEvent> paymentFailedEventConsumerFactory() {
        DefaultKafkaConsumerFactory<Long, PaymentFailedEvent> factory =
                new DefaultKafkaConsumerFactory<>(paymentFailedEventConsumerConfig());
        ErrorHandlingDeserializer<PaymentFailedEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(PaymentFailedEvent.class));
        factory.setValueDeserializer(errorHandlingDeserializer);
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, InventoryReservedEvent> inventoryReservedEventConsumerFactory() {
        DefaultKafkaConsumerFactory<Long, InventoryReservedEvent> factory =
                new DefaultKafkaConsumerFactory<>(inventoryReservedEventConsumerConfig());
        ErrorHandlingDeserializer<InventoryReservedEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(InventoryReservedEvent.class));
        factory.setValueDeserializer(errorHandlingDeserializer);
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, InventoryFailedEvent> inventoryFailedEventConsumerFactory() {
        DefaultKafkaConsumerFactory<Long, InventoryFailedEvent> factory =
                new DefaultKafkaConsumerFactory<>(inventoryFailedEventConsumerConfig());
        ErrorHandlingDeserializer<InventoryFailedEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(InventoryFailedEvent.class));
        factory.setValueDeserializer(errorHandlingDeserializer);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> paymentFailedEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, PaymentFailedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentFailedEventConsumerFactory());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> inventoryReservedEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, InventoryReservedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(inventoryReservedEventConsumerFactory());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> inventoryFailedEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, InventoryFailedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(inventoryFailedEventConsumerFactory());
        return factory;
    }
}