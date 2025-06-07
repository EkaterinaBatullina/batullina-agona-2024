package com.technokratos.event;

import com.technokratos.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record PaymentSucceededEvent (
    String paymentId,
    String orderId,
    BigDecimal amount,
    List<OrderItem> items
) {}