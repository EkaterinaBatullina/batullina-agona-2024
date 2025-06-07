package com.technokratos.event;

public record PaymentFailedEvent (
    String paymentId,
    String orderId,
    String failureReason
) {}