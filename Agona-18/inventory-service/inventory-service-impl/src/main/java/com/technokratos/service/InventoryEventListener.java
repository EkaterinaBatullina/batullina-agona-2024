package com.technokratos.service;

import com.technokratos.event.OrderCreatedEvent;
import com.technokratos.event.PaymentRefundEvent;
import com.technokratos.event.PaymentSucceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryEventListener {
    private final InventoryService service;

    @KafkaListener(
            topics = "payment-succeeded-topic",
            containerFactory = "paymentSucceededEventKafkaListenerContainerFactory"
    )
    public void handlePaymentSucceededEvent(PaymentSucceededEvent event) {
        log.info("PaymentSucceededEvent received: {}", event);
        service.create(event);
    }
}