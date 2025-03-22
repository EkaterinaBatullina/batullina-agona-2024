package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testDirectExchange() {
        amqpTemplate.convertAndSend("direct-exchange", "direct-routing-key1", "Hello, RabbitMQ!");
        Object response = rabbitTemplate.receiveAndConvert("direct-queue1");
        System.out.println("Received from direct-queue1: " + response);
        /*Received from direct-queue1: Hello, RabbitMQ!*/
    }

    @Test
    public void testTopicExchange() {
        amqpTemplate.convertAndSend("topic-exchange", "test1.topic1.test1", "Hello, RabbitMQ!");
        Object response = rabbitTemplate.receiveAndConvert("topic-queue1");
        System.out.println("Received from topic-queue1: " + response);
        /*Received from topic-queue1: Hello, RabbitMQ!*/
    }

    @Test
    public void testDlq() {
        amqpTemplate.convertAndSend("main-queue","Invalid message");
        Object dlqMessage = rabbitTemplate.receiveAndConvert("dlq");
        System.out.println("Received from dlq: " + dlqMessage);
        /*Received from dlq: Invalid message*/
    }
}
