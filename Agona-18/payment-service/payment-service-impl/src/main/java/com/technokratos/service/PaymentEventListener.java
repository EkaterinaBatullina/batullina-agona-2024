package com.technokratos.service;

import com.technokratos.event.OrderCreatedEvent;
import com.technokratos.event.PaymentRefundEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventListener {
    private final PaymentService service;

    @KafkaListener(
            topics = "order-created-topic",
            containerFactory = "orderCreatedEventKafkaListenerContainerFactory"
    )
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent received: {}", event);
        service.create(event);
    }

    @KafkaListener(
            topics = "payment-refund-topic",
            containerFactory = "paymentRefundEventKafkaListenerContainerFactory"
    )
    public void handlePaymentRefundEvent(PaymentRefundEvent event) {
        log.info("PaymentRefundEvent received: {}", event);
        service.refund(event);
    }
}