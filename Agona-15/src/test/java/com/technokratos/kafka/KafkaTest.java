package com.technokratos.kafka;

import com.technokratos.consumer.StarshipEventConsumer;
import com.technokratos.event.StarshipEvent;
import com.technokratos.producer.KafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KafkaTest {
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
         * очищаем состояние consumer между тестами
         */
        consumer.reset();
    }

    @Test
    void sendStringMessage_messagePublished_messageConsumed() throws Exception {
        producer.sendStringMessage(TEST_MESSAGE);

        String result = consumer.getStringFuture()
                .get(5, TimeUnit.SECONDS);

        assertEquals(TEST_MESSAGE, result);
    }

    @Test
    void sendStarshipEvent_eventPublished_eventConsumed() throws Exception {
        producer.sendStarshipEvent(VALID_STARSHIP);

        StarshipEvent result = consumer.getStarshipFuture()
                .get(5, TimeUnit.SECONDS);

        assertEquals(VALID_STARSHIP, result);
    }

    /*
     * Проверка retry + DLT механизма Spring Kafka.
     *
     * При невалидном event происходит retry,
     * после исчерпания попыток сообщение уходит в DLT.
     */
    @Test
    void processInvalidStarshipEvent_retriesExhausted_messageSentToDlt() throws Exception {
        producer.sendStarshipEvent(INVALID_STARSHIP);

        StarshipEvent dlq = consumer.getDlqFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(INVALID_STARSHIP, dlq);
    }
}