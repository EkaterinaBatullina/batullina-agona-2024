package com.technokratos.service;

import com.technokratos.event.PaymentFailedEvent;
import com.technokratos.event.PaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventProducer {
    private final KafkaTemplate<Long, PaymentSucceededEvent> paymentSucceededKafkaTemplate;
    private final KafkaTemplate<Long, PaymentFailedEvent> paymentFailedKafkaTemplate;

    public void sendPaymentSucceededEvent(Long orderId) {
        PaymentSucceededEvent event = new PaymentSucceededEvent(orderId);
        paymentSucceededKafkaTemplate.send("payment-succeeded-topic", orderId, event);
        log.info("PaymentSucceededEvent sent: {}", event);
    }

    public void sendPaymentFailedEvent(Long orderId) {
        PaymentFailedEvent event = new PaymentFailedEvent(orderId);
        paymentFailedKafkaTemplate.send("payment-failed-topic", orderId, event);
        log.info("PaymentFailedEvent sent: {}", event);
    }
}