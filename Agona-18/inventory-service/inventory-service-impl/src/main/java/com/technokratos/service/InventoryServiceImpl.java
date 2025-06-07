package com.technokratos.service;

import com.technokratos.event.PaymentSucceededEvent;
import com.technokratos.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryEventProducer producer;

    public void create(PaymentSucceededEvent event) {
        String orderId = event.orderId();
        List<OrderItem> items = event.items();
        boolean allReserved = true;
        if (allReserved) {
            log.info("Inventory successfully reserved for order {}", orderId);
            producer.sendInventoryReservedEvent(orderId, items);
        } else {
            log.warn("Inventory reservation failed for order {}", orderId);
            producer.sendInventoryFailedEvent(orderId);
        }
    }
}