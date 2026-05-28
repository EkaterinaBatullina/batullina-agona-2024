package org.example.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.example.config.property.KafkaCommonProperties;
import org.example.config.property.KafkaConsumerProperties;
import org.example.dto.StarshipDto;
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
    public Map<String, Object> stringConsumerConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getValueDeserializer());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.isEnableAutoCommit());
        return consumerConfig;
    }

    @Bean
    public Map<String, Object> starshipDtoConsumerConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getStarshipValueDeserializer());
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaProperties.getStarshipTrustedPackages());
        consumerConfig.put(JsonDeserializer.VALUE_DEFAULT_TYPE, kafkaProperties.getStarshipDefaultType());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.isStarshipEnableAutoCommit());
        return consumerConfig;
    }

    @Bean
    public ConsumerFactory<Long, String> stringConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(stringConsumerConfig());
    }

    @Bean
    public ConsumerFactory<Long, StarshipDto> starshipDtoConsumerFactory() {
        DefaultKafkaConsumerFactory<Long, StarshipDto> factory =
                new DefaultKafkaConsumerFactory<>(starshipDtoConsumerConfig());
        ErrorHandlingDeserializer<StarshipDto> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(StarshipDto.class));
        factory.setValueDeserializer(errorHandlingDeserializer);
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> stringKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> starshipDtoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, StarshipDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(starshipDtoConsumerFactory());
        return factory;
    }
}