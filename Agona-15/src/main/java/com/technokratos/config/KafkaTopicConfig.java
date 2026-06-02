package com.technokratos.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/*
 * Конфигурация Kafka-топиков.
 *
 * Позволяет автоматически создавать необходимые топики
 * при старте приложения без ручной настройки брокера.
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic stringEventsTopic() {
        return TopicBuilder.name("string-events")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic starshipEventsTopic() {
        return TopicBuilder.name("starship-events")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic starshipEventsDltTopic() {
        return TopicBuilder.name("starship-events-dlt")
                .partitions(1)
                .replicas(1)
                .build();
    }
}