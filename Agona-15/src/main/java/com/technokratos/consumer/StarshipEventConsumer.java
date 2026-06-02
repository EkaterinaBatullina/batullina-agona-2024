package com.technokratos.consumer;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.technokratos.event.StarshipEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Getter
public class StarshipEventConsumer {
    /*
     * Состояние, используемое только в интеграционных тестах.
     * Хранит последние полученные сообщения из Kafka для проверок.
     *
     *  volatile используется для гарантии видимости между потоками
     * (Kafka listener thread → test thread)
     */
    private volatile String lastReceivedStringMessage;
    private volatile StarshipEvent lastReceivedStarshipMessage;
    private volatile StarshipEvent lastReceivedDlqMessage;

    /*
     * Механизмы синхронизации для тестов.
     * Позволяют дождаться асинхронной обработки Kafka-сообщений.
     */
    private CompletableFuture<String> stringFuture = new CompletableFuture<>();
    private CompletableFuture<StarshipEvent> starshipFuture = new CompletableFuture<>();
    private CompletableFuture<StarshipEvent> dlqFuture = new CompletableFuture<>();

    @KafkaListener(
            topics = "string-events",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenString(ConsumerRecord<Long, String> record, Acknowledgment acknowledgment) {
        lastReceivedStringMessage = record.value();
        stringFuture.complete(record.value());

        acknowledgment.acknowledge();
    }

    @RetryableTopic(
            attempts = "2",
            kafkaTemplate = "kafkaTemplate",
            backoff = @Backoff(delay = 1000, multiplier = 2, maxDelay = 5000)
    )
    @KafkaListener(
            topics = "starship-events",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenStarship(@Payload StarshipEvent event, Acknowledgment acknowledgment) {
        lastReceivedStarshipMessage = event;

        /*
         * Валидация входного события.
         * Если обязательное поле отсутствует - генерируем ошибку,
         * чтобы сработал retry/DLT механизм Kafka.
         */
        if (event.getType() == null) {
            throw new IllegalArgumentException("Type is null");
        }

        starshipFuture.complete(event);

        acknowledgment.acknowledge();
    }

    @KafkaListener(
            topics = "starship-events-dlt",
            groupId = "dlq-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenToDLQ(@Payload StarshipEvent event, Acknowledgment acknowledgment) {
        lastReceivedDlqMessage = event;
        dlqFuture.complete(event);

        acknowledgment.acknowledge();
    }

    /*
     * Очистка состояния между интеграционными тестами.
     *
     * Используется только в тестах, в production не требуется,
     * так как Kafka listener должен быть stateless.
     */
    public void reset() {
        lastReceivedStringMessage = null;
        lastReceivedStarshipMessage = null;
        lastReceivedDlqMessage = null;

        stringFuture = new CompletableFuture<>();
        starshipFuture = new CompletableFuture<>();
        dlqFuture = new CompletableFuture<>();
    }
}