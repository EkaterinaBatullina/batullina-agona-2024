package com.technokratos.service;

import com.technokratos.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventListener {
    private final OrderEventProducer producer;
    private final OrderService service;

    @KafkaListener(
            topics = "inventory-reserved-topic",
            containerFactory = "inventoryReservedEventKafkaListenerContainerFactory"
    )
    public void handleInventoryReservedEvent(InventoryReservedEvent event) {
        log.info("InventoryReservedEvent received: {}", event);
        service.updateOrderStatus(event.orderId(), "INVENTORY_RESERVED");
    }

    @KafkaListener(
            topics = "inventory-failed-topic",
            containerFactory = "inventoryFailedEventKafkaListenerContainerFactory"
    )
    public void handleInventoryFailedEvent(InventoryFailedEvent event) {
        log.warn("InventoryFailedEvent received for order {}", event.orderId());
        service.updateOrderStatus(event.orderId(), "INVENTORY_FAILED");
        producer.sendPaymentRefundEvent(event.orderId());
    }

    @KafkaListener(
            topics = "payment-failed-topic",
            containerFactory = "paymentFailedEventKafkaListenerContainerFactory"
    )
    public void handlePaymentFailedEvent(PaymentFailedEvent event) {
        log.info("PaymentFailedEvent received: {}", event);
        service.updateOrderStatus(event.orderId(), "PAYMENT_FAILED");
    }
}