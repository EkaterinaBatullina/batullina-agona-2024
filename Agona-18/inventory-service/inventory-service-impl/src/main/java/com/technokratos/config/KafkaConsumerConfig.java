package com.technokratos.config;

import com.technokratos.config.property.KafkaCommonProperties;
import com.technokratos.config.property.KafkaConsumerProperties;
import com.technokratos.event.PaymentSucceededEvent;
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
    public Map<String, Object> paymentSucceededEventConsumerConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getPaymentSucceededEventValueDeserializer());
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaProperties.getTrustedPackages());
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, kafkaProperties.getPaymentSucceededEventDefaultType());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.isEnableAutoCommit());
        return consumerConfig;
    }

    @Bean
    public ConsumerFactory<Long, PaymentSucceededEvent> paymentSucceededEventConsumerFactory() {
        DefaultKafkaConsumerFactory<Long, PaymentSucceededEvent> factory =
                new DefaultKafkaConsumerFactory<>(paymentSucceededEventConsumerConfig());
        ErrorHandlingDeserializer<PaymentSucceededEvent> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(PaymentSucceededEvent.class));
        factory.setValueDeserializer(errorHandlingDeserializer);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> paymentSucceededEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, PaymentSucceededEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentSucceededEventConsumerFactory());
        return factory;
    }
}