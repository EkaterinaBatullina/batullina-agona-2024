package com.technokratos.event;

public record InventoryFailedEvent (
    String orderId,
    String failureReason
) {}