package com.technokratos.event;

import java.math.BigDecimal;

public record PaymentRefundEvent (
    String refundId,
    String paymentId,
    String orderId,
    BigDecimal refundAmount,
    String reason
) {}