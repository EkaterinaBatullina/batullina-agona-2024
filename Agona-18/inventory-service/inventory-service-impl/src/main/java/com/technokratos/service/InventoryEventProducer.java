package com.technokratos.service;

import com.technokratos.event.InventoryFailedEvent;
import com.technokratos.event.InventoryReservedEvent;
import com.technokratos.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryEventProducer {
    private final KafkaTemplate<Long, InventoryReservedEvent> inventoryReservedKafkaTemplate;
    private final KafkaTemplate<Long, InventoryFailedEvent> inventoryFailedKafkaTemplate;

    public void sendInventoryReservedEvent(String orderId, List<OrderItem> items) {
        InventoryReservedEvent event = new InventoryReservedEvent(
                UUID.randomUUID().toString(),
                orderId,
                items);
        inventoryReservedKafkaTemplate.send("inventory-reserved-topic", Long.valueOf(orderId), event);
        log.info("InventoryReservedEvent sent: {}", event);
    }

    public void sendInventoryFailedEvent(String orderId) {
        InventoryFailedEvent event = new InventoryFailedEvent(
                UUID.randomUUID().toString(),
                orderId);
        inventoryFailedKafkaTemplate.send("inventory-failed-topic", Long.valueOf(orderId), event);
        log.info("InventoryFailedEvent sent: {}", event);
    }
}