package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RabbitMQTest {
    private static final String TEST_MESSAGE = "Hello, RabbitMQ!";
    private static final String INVALID_MESSAGE = "Invalid message";
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testDirectExchange() {
        amqpTemplate.convertAndSend("direct-exchange", "direct-routing-key1", TEST_MESSAGE);
        Object response = rabbitTemplate.receiveAndConvert("direct-queue1");
        assertNotNull(response, "Message should not be null");
        assertEquals(TEST_MESSAGE, response, "Received message should match the sent message");
    }

    @Test
    public void testTopicExchange() {
        amqpTemplate.convertAndSend("topic-exchange", "test1.topic1.test1", TEST_MESSAGE);
        Object response = rabbitTemplate.receiveAndConvert("topic-queue1");
        assertNotNull(response, "Message should not be null");
        assertEquals(TEST_MESSAGE, response, "Received message should match the sent message");
    }

    @Test
    public void testDlq() {
        amqpTemplate.convertAndSend("main-queue", INVALID_MESSAGE);
        Object dlqMessage = rabbitTemplate.receiveAndConvert("dlq");
        assertNotNull(dlqMessage, "DLQ message should not be null");
        assertEquals(INVALID_MESSAGE, dlqMessage, "Received DLQ message should match the invalid message");
    }
}