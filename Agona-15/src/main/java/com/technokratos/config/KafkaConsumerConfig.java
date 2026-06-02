package com.technokratos.config;

import com.technokratos.config.property.KafkaCommonProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import com.technokratos.config.property.KafkaConsumerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
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
    public Map<String, Object> consumerConfig() {
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, commonProperties.getBootstrapServers());
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getValueDeserializer());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());

        /*
         * Автокоммит отключён.
         *
         * Offset подтверждается только после успешной обработки
         * сообщения через Acknowledgment acknowledge().
         *
         * Такой подход предотвращает потерю сообщений при падении
         * consumer после получения записи, но до завершения
         * бизнес-логики.
         */
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        /*
         * Ограничение списка доверенных пакетов для безопасной
         * десериализации JSON-сообщений.
         *
         * Предотвращает создание произвольных классов,
         * поступивших из Kafka.
         */
        consumerConfig.put(JsonDeserializer.TRUSTED_PACKAGES, kafkaProperties.getTrustedPackages());

        /*
         * Сопоставление значения Kafka-заголовка "__TypeId__"
         * с конкретным Java-классом события.
         *
         * Позволяет использовать единый ConsumerFactory
         * для обработки нескольких типов событий без создания
         * отдельной конфигурации под каждый DTO.
         */
        consumerConfig.put(JsonDeserializer.TYPE_MAPPINGS, kafkaProperties.getTypeMappings());
        return consumerConfig;
    }

    @Bean
    public ConsumerFactory<Long, Object> consumerFactory() {
        DefaultKafkaConsumerFactory<Long, Object> factory = new DefaultKafkaConsumerFactory<>(consumerConfig());

        /*
         * Ошибки десериализации перехватываются до попадания
         * сообщения в listener.
         *
         * Это позволяет корректно обрабатывать неконсистентные
         * сообщения и использовать retry/DLT-механизмы Spring Kafka.
         */
        ErrorHandlingDeserializer<Object> errorHandlingDeserializer =
                new ErrorHandlingDeserializer<>(
                        new JsonDeserializer<>()
                );

        factory.setValueDeserializer(errorHandlingDeserializer);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, Object>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        /*
         * Ручное подтверждение обработки сообщения.
         *
         * Offset фиксируется только после успешного завершения
         * бизнес-логики, что позволяет повторно получить сообщение
         * при сбое до момента acknowledge().
         */
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
