package org.example;

import org.example.dto.StarshipDto;
import org.example.listener.KafkaListeners;
import org.example.producer.KafkaProducer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.annotation.EnableKafka;

import java.time.Duration;

import org.awaitility.Awaitility;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableKafka
public class KafkaTest {
    private static final String TEST_MESSAGE = "Test String Message";
    private static final StarshipDto VALID_STARSHIP = new StarshipDto("Enterprise", "Cruiser");
    private static final StarshipDto INVALID_STARSHIP = new StarshipDto("Enterprise", null);
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final int RETRY_COUNT = 2;
    @SpyBean
    private KafkaListeners listener;
    @Autowired
    private KafkaProducer producer;

    @Test
    public void testSendStringMessage() {
        producer.sendStringMessage(TEST_MESSAGE);
        Awaitility.await()
                .atMost(TIMEOUT)
                .untilAsserted(() -> {
                    String receivedStringMessage = listener.getLastReceivedStringMessage();
                    assertNotNull(receivedStringMessage, "Message was not received");
                    assertEquals(TEST_MESSAGE, receivedStringMessage);
                });
    }

    @Test
    public void testSendStarshipMessage() {
        producer.sendStarshipMessage(VALID_STARSHIP);
        Awaitility.await()
                .atMost(TIMEOUT)
                .untilAsserted(() -> {
                    StarshipDto receivedStarshipMessage = listener.getLastReceivedStarshipDto();
                    assertNotNull(receivedStarshipMessage, "Message was not received");
                    assertEquals(VALID_STARSHIP, receivedStarshipMessage);
                });
    }

    @Test
    public void testRetryForStarshipMessage() {
        producer.sendStarshipMessage(INVALID_STARSHIP);
        Awaitility.await()
                .atMost(TIMEOUT)
                .untilAsserted(() ->
                        verify(listener, times(RETRY_COUNT)).listenStarship(any())
                );
    }

    @Test
    public void testMessageSentToDLTAfterFailure() {
        producer.sendStarshipMessage(INVALID_STARSHIP);
        Awaitility.await()
                .atMost(TIMEOUT)
                .untilAsserted(() -> {
                    assertEquals(INVALID_STARSHIP, listener.getLastReceivedMessageFromDLQ());
                });
    }
}