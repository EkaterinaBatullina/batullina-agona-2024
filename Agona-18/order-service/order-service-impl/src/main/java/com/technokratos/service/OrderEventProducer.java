package com.technokratos.service;

import com.technokratos.event.OrderCreatedEvent;
import com.technokratos.event.PaymentFailedEvent;
import com.technokratos.event.PaymentRefundEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventProducer {
    private final KafkaTemplate<Long, OrderCreatedEvent> orderCreatedEventKafkaTemplate;
    private final KafkaTemplate<Long, PaymentRefundEvent> paymentRefundEventKafkaTemplate;

    public void sendOrderCreatedEvent(String orderId) {
        OrderCreatedEvent event = new OrderCreatedEvent(orderId);
        orderCreatedEventKafkaTemplate.send("order-created-topic", orderId, event);
        log.info("OrderCreatedEvent sent: {}", event);
    }

    public void sendPaymentRefundEvent(String orderId) {
        PaymentRefundEvent event = new PaymentRefundEvent(orderId);
        paymentRefundEventKafkaTemplate.send("payment-refund-topic", orderId, event);
        log.info("PaymentRefundEvent sent: {}", event);
    }
}