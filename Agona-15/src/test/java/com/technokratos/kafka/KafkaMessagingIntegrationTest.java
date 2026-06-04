package com.technokratos.kafka;

import com.technokratos.consumer.StarshipEventConsumer;
import com.technokratos.event.StarshipEvent;
import com.technokratos.kafka.base.AbstractKafkaIntegrationTest;
import com.technokratos.producer.KafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KafkaMessagingIntegrationTest extends AbstractKafkaIntegrationTest {
    private static final String TEST_MESSAGE = "Test String Message";
    private static final StarshipEvent VALID_STARSHIP = new StarshipEvent("Enterprise", "Cruiser");
    private static final StarshipEvent INVALID_STARSHIP = new StarshipEvent("Enterprise", null);
    @Autowired
    private KafkaProducer producer;
    @Autowired
    private StarshipEventConsumer consumer;

    @BeforeEach
    void clearState() {

        /*
         * Изоляция тестов:
         * очищаем состояние consumer перед каждым запуском.
         * Это позволяет избежать влияния результатов
         * предыдущих тестов на последующие.
         */
        consumer.reset();
    }

    @Test
    void sendStringMessage_messagePublished_messageConsumed() throws Exception {

        /*
         * Проверяем полный цикл:
         * producer - Kafka - consumer.
         */
        producer.sendStringMessage(TEST_MESSAGE);

        String result = consumer.getStringFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(TEST_MESSAGE, result);
    }

    @Test
    void sendStarshipEvent_eventPublished_eventConsumed() throws Exception {

        /*
         * Проверяем публикацию и обработку
         * корректного объекта StarshipEvent.
         */
        producer.sendStarshipEvent(VALID_STARSHIP);

        StarshipEvent result = consumer.getStarshipFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(VALID_STARSHIP, result);
    }

    @Test
    void processInvalidStarshipEvent_retriesExhausted_messageSentToDlt() throws Exception {

        /*
         * Проверяем механизм RetryableTopic:
         *
         * 1. Consumer получает невалидное сообщение.
         * 2. Обработка завершается ошибкой.
         * 3. Spring Kafka выполняет повторные попытки.
         * 4. После исчерпания retry сообщение
         *    перенаправляется в DLT (Dead Letter Topic).
         */
        producer.sendStarshipEvent(INVALID_STARSHIP);

        StarshipEvent dlq = consumer.getDlqFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(INVALID_STARSHIP, dlq);
    }
}