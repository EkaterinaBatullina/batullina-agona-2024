package com.technokratos.event;

import com.technokratos.model.OrderItem;

import java.util.List;

public record InventoryReservedEvent (
    String reservationId,
    String orderId,
    List<OrderItem> reservedItems
) {}