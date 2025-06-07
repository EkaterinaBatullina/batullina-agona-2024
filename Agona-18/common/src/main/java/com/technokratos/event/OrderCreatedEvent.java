package com.technokratos.event;

import com.technokratos.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record OrderCreatedEvent (
    String orderId,
    String userId,
    List<OrderItem> items,
    BigDecimal totalAmount
){}