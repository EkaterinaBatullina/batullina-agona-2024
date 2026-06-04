package com.technokratos.kafka.base;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class AbstractKafkaIntegrationTest {

    /*
     * Поднимает реальный Kafka broker в Docker для интеграционных тестов.
     * Используется вместо локальной Kafka (localhost:9092), чтобы тесты были изолированы.
     */
    private static final KafkaContainer kafka =
            new KafkaContainer(
                    DockerImageName.parse("apache/kafka:3.8.0")
            );

    /*
     * Ручной старт контейнера ДО запуска Spring Context.
     *
     * Spring Kafka (Producer/Consumer) подключается к уже запущенному broker'у,
     * иначе возможны Connection/Timeout ошибки.
     */
    static {
        kafka.start();
    }

    /*
     * Динамическая подмена property spring.kafka.bootstrap-servers
     * значением из Testcontainers Kafka.
     *
     * Это гарантирует, что Spring подключится НЕ к localhost:9092,
     * а к реально запущенному контейнеру.
     */
    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.kafka.bootstrap-servers",
                kafka::getBootstrapServers
        );
    }
}